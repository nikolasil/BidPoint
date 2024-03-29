package com.bidpoint.backend.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.Set;

@Data
@AllArgsConstructor
public class UserOutputDto {
    private String username;
    private String firstname;
    private String lastname;
    private boolean approved;
    private Set<String> roles;
    private String address;
    private String phone;
    private String mail;
    private String afm;
}
