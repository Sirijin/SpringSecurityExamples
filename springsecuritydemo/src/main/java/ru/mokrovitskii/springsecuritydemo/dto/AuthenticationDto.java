package ru.mokrovitskii.springsecuritydemo.dto;

import lombok.Data;

@Data
public class AuthenticationDto {

    private String email;
    private String password;
}
