package com.bidpoint.backend.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@AllArgsConstructor
public class UserOutputDto {
    private String firstname;
    private String lastname;
    private String username;
    private Collection<String> roles;
    private String address;
    private String phone;
    private String mail;
    private String afm;
}
