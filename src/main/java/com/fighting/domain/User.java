package com.fighting.domain;

import com.fighting.Tool.Tool;

public class User {
    private String id;
    private String name;
    private String password;
    private Boolean state;
    private String phone;

    public User() {
        this.id = Tool.generateId();
        this.state = true;
    }

    public User(String name, String password, String phone) {
        this.id = Tool.generateId();
        this.name = name;
        this.password = password;
        this.phone = phone;
        this.state = true;
    }

    public String getId() {
        return id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}

