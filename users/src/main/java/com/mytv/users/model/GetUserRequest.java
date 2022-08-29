package com.mytv.users.model;

import java.util.Optional;

public class GetUserRequest {

    Optional<String> fname;
    Optional<String> lname;
    Optional<String> email;
    Optional<String> service;

    public GetUserRequest(){
        fname = Optional.empty();
        lname = Optional.empty();
        email = Optional.empty();
        service = Optional.empty();
    }

    public Optional<String> getFname() {
        return fname;
    }

    public void setFname(Optional<String> fname) {
        this.fname = fname;
    }

    public Optional<String> getLname() {
        return lname;
    }

    public void setLname(Optional<String> lname) {
        this.lname = lname;
    }

    public Optional<String> getEmail() {
        return email;
    }

    public void setEmail(Optional<String> email) {
        this.email = email;
    }

    public Optional<String> getService() {
        return service;
    }

    public void setService(Optional<String> service) {
        this.service = service;
    }
}
