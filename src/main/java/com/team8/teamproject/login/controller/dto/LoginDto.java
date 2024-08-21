package com.team8.teamproject.login.controller.dto;


import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class LoginDto {
    @NonNull
    private String userId;
}
