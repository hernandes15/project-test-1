package com.project.test.model.dto;

import lombok.Data;

import java.util.UUID;


@Data
public class UserDto {
    private int userId;
    private UUID userUuid;
    private String name;
    private String username;
    private String password;
    private String oldPassword;
    private String roles;

}
