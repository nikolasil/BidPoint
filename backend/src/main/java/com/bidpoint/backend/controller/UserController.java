package com.bidpoint.backend.controller;

import com.bidpoint.backend.converter.UserDtoInputToUserConverter;
import com.bidpoint.backend.converter.UserToUserDtoOutputConverter;
import com.bidpoint.backend.dto.UserDtoInput;
import com.bidpoint.backend.dto.UserDtoOutput;
import com.bidpoint.backend.entity.User;
import com.bidpoint.backend.service.UserService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Log4j2
public class UserController {

    private final UserService userService;
    private final ConversionService conversionService;

    @PostMapping
    public ResponseEntity<UserDtoOutput> saveUser(@RequestBody UserDtoInput userDtoInput) {
        User user = conversionService.convert(userDtoInput,User.class);
        assert user != null;
        userService.saveUser(user);
        userService.addRoleToUser(user.getUsername(), "visitor");
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user").toUriString());
        return ResponseEntity.created(uri).body(conversionService.convert(userService.getUser(user.getUsername()),UserDtoOutput.class));
    }

    @GetMapping()
    public ResponseEntity<UserDtoOutput> getUser(@RequestParam(name = "username") String username){
        return ResponseEntity.ok().body(conversionService.convert(userService.getUser(username),UserDtoOutput.class));
    }

    @GetMapping("/me")
    public ResponseEntity<UserDtoOutput> getUser(HttpServletRequest request) {

        String authorizationHeader = request.getHeader(AUTHORIZATION);

        String refresh_token = authorizationHeader.substring("Bearer ".length());
        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(refresh_token);
        String username = decodedJWT.getSubject();

        return ResponseEntity.ok().body(conversionService.convert(userService.getUser(username),UserDtoOutput.class));
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserDtoOutput>> getUsers(){
        List<UserDtoOutput> list = userService.getUsers().stream().map(user -> conversionService.convert(user,UserDtoOutput.class)).toList();
        return ResponseEntity.ok().body(list);
    }

    @PostMapping("/role")
    public ResponseEntity<UserDtoOutput>addRoleToUser(@RequestParam(name = "username") String username, @RequestParam(name = "roleName") String roleName){
        return ResponseEntity.ok().body(conversionService.convert(userService.addRoleToUser(username, roleName),UserDtoOutput.class));
    }

    @DeleteMapping("/role")
    public ResponseEntity<UserDtoOutput>removeRoleFromUser(@RequestParam(name = "username") String username, @RequestParam(name = "roleName") String roleName){
        return ResponseEntity.ok().body(conversionService.convert(userService.removeRoleFromUser(username, roleName),UserDtoOutput.class));
    }
}
