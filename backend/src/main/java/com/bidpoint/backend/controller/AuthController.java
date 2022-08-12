package com.bidpoint.backend.controller;

import com.bidpoint.backend.dto.user.UserOutputDto;
import com.bidpoint.backend.exception.auth.SignInException;
import com.bidpoint.backend.exception.auth.TokenIsMissingException;
import com.bidpoint.backend.service.user.UserService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.bidpoint.backend.util.AuthUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final AuthUtil authUtil;

    @GetMapping("/me")
    public ResponseEntity<UserOutputDto> getUser(HttpServletRequest request, HttpServletResponse response) {
        String username = authUtil.decodeAuthorizationHeader(request.getHeader(AUTHORIZATION)).getSubject();
        UserOutputDto user = userService.getUser(username);
        return ResponseEntity.ok().body(user);
    }

    @GetMapping("/refresh-token")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer "))
            throw new TokenIsMissingException();

        try {
            String username = authUtil.decodeAuthorizationHeader(authorizationHeader).getSubject();

            UserOutputDto user = userService.getUser(username);
            String access_token = JWT.create()
                    .withSubject(user.getUsername())
                    .withExpiresAt(new Date(System.currentTimeMillis() + 10 - 60 * 1000))
                    .withIssuer(request.getRequestURL().toString())
                    .withClaim("roles",user.getRoles().stream().toList())
                    .sign(authUtil.getAlgorithm());
            Map<String,String> tokens = new HashMap<>();
            tokens.put("access_token",access_token);
            tokens.put("refresh_token",authorizationHeader.substring("Bearer ".length()));
            response.setContentType(APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getOutputStream(),tokens);
        } catch (Exception e) {
            throw new SignInException(e);
        }
    }
}
