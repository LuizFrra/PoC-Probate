package com.poc.jwt.Models;

public class LoginRequest {

    public String username;

    public String password;
    
    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public LoginRequest() {

    }
}
