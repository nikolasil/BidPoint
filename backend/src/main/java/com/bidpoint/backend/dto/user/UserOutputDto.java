package com.bidpoint.backend.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserOutputDto {
    private String firstname;
    private String lastname;
    private String username;
    private Collection<String> roles;
}
