package com.javanik.in.userauthservice.dto;


import lombok.Data;

@Data
public class AuthRequest {

     private String userName;
     private String password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public AuthRequest() {
    }

    public AuthRequest(String userName, String password) {
        this.userName=userName;
        this.password = password;
    }



    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
