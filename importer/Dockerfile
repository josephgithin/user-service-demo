
FROM python:latest
LABEL Maintainer="githin jose"

COPY main.py ./

RUN pip install sqlalchemy pymysql google-cloud-storage gcsfs dask pandas

CMD [ "python", "./main.py"]