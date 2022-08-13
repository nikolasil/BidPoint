package com.bidpoint.backend.auth.dto;

import com.bidpoint.backend.user.dto.UserOutputDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class AuthOutputDto {
    private String access_token;
    private String refresh_token;
    private UserOutputDto user;
}
