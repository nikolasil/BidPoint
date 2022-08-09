package com.bidpoint.backend.dto.auth;

import com.bidpoint.backend.dto.user.UserOutputDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthDto {
    private String access_token;
    private String refresh_token;
    private UserOutputDto user;
}
