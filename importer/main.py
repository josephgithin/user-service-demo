from time import time
from sqlalchemy import MetaData, create_engine,Table

from sqlalchemy.sql import select,or_,and_
import pymysql
import dask.dataframe as dd
from dask.dataframe import read_sql_query
from google.cloud import storage
from google.oauth2 import service_account
from datetime import datetime
import os

import gcsfs
import json

import logging

logging.basicConfig()
logging.getLogger().setLevel(logging.DEBUG)

#################################
# Getting state from storage    #
#################################
def get_state():
    fs = gcsfs.GCSFileSystem(token=secretpath)
    exists = fs.exists(state_file)
    if exists:
        with fs.open(state_file) as f:
            state = json.load(f)
            logging.info("state exists. {0}".format(state))
            return state
    else:
        default_state_date = os.getenv("DEFAULT_STATE_DATE","1970-01-01 00:00:00")
        default_dict = {
            "last_updated_date":default_state_date,
            "last_added_date": default_state_date
        }
        if not fs.exists(state_folder):
            fs.mkdir(state_folder)
        with fs.open(state_file, 'wb') as f:
            f.write(bytes(json.dumps(default_dict),"utf-8"))
            logging.info("created new state.")
            return default_dict
            
            
#################################
# Setting state to storage    #
#################################
def set_state(state_dict):
    fs = gcsfs.GCSFileSystem(token=secretpath)
    with fs.open(state_file, 'wb') as f:
        f.write(bytes(json.dumps(state_dict),"utf-8"))

if __name__ == "__main__":
    
    ##############################################
    # load config params                        ##
    ##############################################
    
    db_url =  os.getenv("DB_URL",'mysql+pymysql://root:zSXWr1nGlk@127.0.0.1/telus_db');

    table_name = os.getenv("TABLE_NAME","users")
    update_column = os.getenv("UPDATE_COLUMN","updated_on")
    insert_column = os.getenv("INSERT_COLUMN","added_on")
    interval_in_minutes = os.getenv("INTERVAL_IN_MINUTES",5)
    output_path = os.getenv("OUTPUT_PATH","user_importer_data")
    secretpath = os.getenv("SECRET_PATH","user-importer-5924b8fe1a34.json")
    columns = os.getenv("COLUMNS","id,fname,lname,email,service")
    index = os.getenv("INDEX_COLUMN","id")
    default_state_date = os.getenv("DEFAULT_STATE_DATE","1970-01-01 00:00:00")
    storage_scheme = os.getenv("STORAGE_SCHEME","gs")


    column_list = columns.split(",")

    state_folder = "{0}/_state".format(output_path)
    state_file = "{0}/table_state.json".format(state_folder)


    ###################################################
    # Build metadata                                  # 
    ###################################################

    sqlEngine = create_engine(db_url, pool_recycle=3600)
    dbConnection = sqlEngine.connect().execution_options(stream_results=True)

    batch_state_row = sqlEngine.execute("""
        select max({0}) as u,max({1}) as a from {2}
    """.format(update_column,insert_column,table_name)).fetchone()

    dbConnection.close()

    batch_u = batch_state_row['u']
    batch_a = batch_state_row['a']

    meta = MetaData()
    users = Table('users', meta, autoload=True, autoload_with=sqlEngine)

    columns_meta = [c for c in users.columns if c.name in column_list]
    up_query = [c for c in users.columns if c.name == update_column]
    add_query = [c for c in users.columns if c.name == insert_column]

    if len(up_query) > 0:
        update_column_meta = up_query[0]

    if len(add_query) > 0:
        add_column_meta = add_query[0]

    #########################################################
    # Build Query                                           #
    #########################################################

    stm = select(columns_meta)
    state = get_state()
    last_added_date = state["last_added_date"]
    last_updated_date = state["last_updated_date"]
    logging.info("last updated date: {0}, last added date: {1}",last_updated_date,last_added_date)
    stm  = stm.where(or_(update_column_meta > last_updated_date,add_column_meta > last_added_date))

    #########################################################
    # Extract data                                          #
    #########################################################

    data = read_sql_query(
    stm, db_url, npartitions=1, index_col=index)


    ########################################################
    # Load Data                                            #
    ########################################################

    data_size = len(data.index)

    if data_size > 0:    
        storage_options={'token': secretpath}

        dt_string = datetime.now().strftime("%d-%m-%Y-%H-%M-%S")

        output_file = '{0}-data{1}-*.csv'.format(table_name,dt_string)
        data.to_csv("{2}://{0}/{1}".format(output_path,output_file,storage_scheme),storage_options=storage_options)

        set_state({
                    "last_updated_date":str(batch_u),
                    "last_added_date": str(batch_a)
                })
        logging.info("exported {0} records.".format(data_size))
    else:
        logging.info("no records to export.")