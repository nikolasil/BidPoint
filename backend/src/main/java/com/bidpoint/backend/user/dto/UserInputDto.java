package com.bidpoint.backend.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
public class UserInputDto {
    private static final String USERNAME_PATTERN = "^[a-zA-Z0-9]*$";
    private static final String PASSWORD_PATTERN = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_=+{}/])(?=\\S+$).{8,}";

    @NotEmpty(message = "firstname cannot be empty")
    private String firstname;

    @NotEmpty(message = "lastname cannot be empty")
    private String lastname;

    @NotEmpty(message = "username cannot be empty")
    @Pattern(regexp = USERNAME_PATTERN, message ="Please provide a valid username")
    private String username;

    @NotEmpty(message = "password cannot be empty")
    @Size(min = 8, message = "password must be at least 8 characters")
    @Pattern(regexp = PASSWORD_PATTERN, message ="Please provide a valid password")
    private String password;

    @NotEmpty(message = "address cannot be empty")
    private String address;

    @NotEmpty(message = "phone cannot be empty")
    @NotNull
    private String phone;

    @NotEmpty(message = "mail cannot be empty")
    @Email
    private String mail;

    @NotEmpty(message = "afm cannot be empty")
    private String afm;
}
