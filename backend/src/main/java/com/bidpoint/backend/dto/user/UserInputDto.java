package com.bidpoint.backend.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInputDto {
    private String firstname;
    private String lastname;
    private String username;
    private String password;
    private String address;
    private String phone;
    private String mail;
    private String afm;
}
