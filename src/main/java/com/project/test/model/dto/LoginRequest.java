package com.project.test.model.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;


@Data
public class LoginRequest {
    @NotBlank
        private String usernameOrEmail;

    @NotBlank
    private String password;

    @NotBlank
    private String domain;
    
    private String branchCode;

    private String localhostName;

    private String workGroup;

    private String loginIp;
}
