package com.bidpoint.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDtoOutput {
    private String firstname;
    private String lastname;
    private String username;
    private Collection<String> roles;
}
