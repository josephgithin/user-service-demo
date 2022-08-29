package com.mytv.users.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * User model and Entity
 */
@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    String fname;
    String lname;
    String email;
    String service;
    LocalDateTime addedOn;
    LocalDateTime updatedOn;

    public User() {

    }

    public User(Long id, String fname, String lname, String email, String service, LocalDateTime addedOn, LocalDateTime updatedOn) {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.service = service;
        this.addedOn = addedOn;
        this.updatedOn = updatedOn;
    }

    public static User from(GetUserRequest request) {
        User user = new User();
        user.setEmail(request.getEmail().isPresent() ? request.getEmail().get() : null);
        user.setFname(request.getFname().isPresent() ? request.getFname().get() : null);
        user.setLname(request.getLname().isPresent() ? request.getLname().get() : null);
        user.setService(request.getService().isPresent() ? request.getService().get() : null);
        return user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public LocalDateTime getAddedOn() {
        return addedOn;
    }

    public void setAddedOn(LocalDateTime addedOn) {
        this.addedOn = addedOn;
    }

    public LocalDateTime getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(LocalDateTime updatedOn) {
        this.updatedOn = updatedOn;
    }
}
