package com.example.Baesh.DTO;

import lombok.Data;

@Data
public class AuthResponse {
    private String userToken;

    public AuthResponse(String userToken){
        this.userToken=userToken;
    }
}
