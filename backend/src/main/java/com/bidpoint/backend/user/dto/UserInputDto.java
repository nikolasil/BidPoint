package com.bidpoint.backend.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
public class UserInputDto {
    @NotEmpty(message = "firstname cannot be empty")
    private String firstname;

    @NotEmpty(message = "lastname cannot be empty")
    private String lastname;

    @NotEmpty(message = "username cannot be empty")
    private String username;

    @NotEmpty(message = "password cannot be empty")
    private String password;

    @NotEmpty(message = "address cannot be empty")
    private String address;

    @NotEmpty(message = "phone cannot be empty")
    private String phone;

    @NotEmpty(message = "mail cannot be empty")
    private String mail;

    @NotEmpty(message = "afm cannot be empty")
    private String afm;
}
