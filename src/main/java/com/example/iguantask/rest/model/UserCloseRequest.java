package com.example.iguantask.rest.model;

public class UserCloseRequest {
    private String username;

    private String email;

    private String firstname;

    private String lastname;

    private String phone;

    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
}
