package com.bidpoint.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthDto {
    private String access_token;
    private String refresh_token;
    private UserDtoOutput user;
}
