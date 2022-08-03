package com.bidpoint.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDtoInput {
    private String firstname;
    private String lastname;
    private String username;
    private String password;
    private Collection<String> roles;
}
