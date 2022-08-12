package com.bidpoint.backend.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.bidpoint.backend.dto.auth.AuthDto;
import com.bidpoint.backend.dto.user.UserOutputDto;
import com.bidpoint.backend.util.AuthUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.stream.Collectors;
import com.bidpoint.backend.service.user.UserService;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final ConversionService conversionService;
    private final AuthUtil authUtil;
    private final UserService userService;

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager,UserService userService,ConversionService conversionService,AuthUtil authUtil){
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.conversionService = conversionService;
        this.authUtil = authUtil;
    }

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String requestData = request.getReader().lines().collect(Collectors.joining());

        JSONObject jsonObject = new JSONObject(requestData);
        String username = (String) jsonObject.get("username");
        String password = (String) jsonObject.get("password");

        log.info("Username is: {}",username);
        log.info("Password is: {}",password);

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,password);

        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        User user = (User)authentication.getPrincipal();

        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(
            response.getOutputStream(),
            new AuthDto(
                authUtil.createAccessToken(user,request),
                authUtil.createRefreshToken(user,request),
                conversionService.convert(userService.getUser(user.getUsername()), UserOutputDto.class)
            )
        );

    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        super.unsuccessfulAuthentication(request, response, failed);
    }
}
