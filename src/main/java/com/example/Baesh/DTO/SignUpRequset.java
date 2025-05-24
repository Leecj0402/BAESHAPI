package com.example.Baesh.DTO;

import lombok.Data;

@Data
public class SignUpRequset {
    private String userId;
    private String password;
    private String name;
    private String email;
    private String phone_number;
    private String residence;
    private String rule;
}
