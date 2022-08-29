package com.mytv.users.model;

import java.time.LocalDateTime;

public class UserDTO {

    private Long id;
    private String firstName;
    private String lastname;
    private String email;
    private String service;
    private LocalDateTime addedOn;
    private LocalDateTime updatedOn;

    public UserDTO(Long id, String firstName, String lastname, String email, String service, LocalDateTime addedOn, LocalDateTime updatedOn) {
        this.id = id;
        this.firstName = firstName;
        this.lastname = lastname;
        this.email = email;
        this.service = service;
        this.addedOn = addedOn;
        this.updatedOn = updatedOn;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
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

    public static  UserDTO from(User user) {
        return new UserDTO(user.getId(),user.getFname(), user.getLname(),
                user.getEmail(),user.getService(),user.getAddedOn(),user.getUpdatedOn());
    }
}
