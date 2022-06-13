package com.project.kn.scrumsimulator.entity;

import java.util.Date;

public class PlayerEntity extends AbstractObject {

    private String login;
    private String password;
    private String email;
    private Date createdAt;

    public PlayerEntity(int id, String login, String password, String email, Date createdAt) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.email = email;
        this.createdAt = createdAt;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
