package com.example.valeriaserber.trainingapp.model;

public class SignUpObject {

    private String username;
    private String password;

    public SignUpObject(String username, String password){
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
