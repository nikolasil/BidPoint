package com.auction.platform.backend.controller;

import com.auction.platform.backend.entity.Role;
import com.auction.platform.backend.entity.User;
import com.auction.platform.backend.service.UserService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<User>saveUser(@RequestBody User user) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user").toUriString());
        return ResponseEntity.created(uri).body(userService.saveUser(user));
    }

    @GetMapping()
    public ResponseEntity<User> getUser(@RequestParam(name = "username") String username){
        return ResponseEntity.ok().body(userService.getUser(username));
    }

    @GetMapping("/me")
    public ResponseEntity<User> getUser(HttpServletRequest request) {

        String authorizationHeader = request.getHeader(AUTHORIZATION);

        String refresh_token = authorizationHeader.substring("Bearer ".length());
        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(refresh_token);
        String username = decodedJWT.getSubject();

        return ResponseEntity.ok().body(userService.getUser(username));
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> getUsers(){
        return ResponseEntity.ok().body(userService.getUsers());
    }

    @PostMapping("/add-role")
    public ResponseEntity<?>addRoleToUser(@RequestParam(name = "username") String username, @RequestParam(name = "roleName") String roleName){
        return ResponseEntity.ok().body(userService.addRoleToUser(username, roleName).getRoles());
    }

    @PostMapping("/remove-role")
    public ResponseEntity<?>removeRoleFromUser(@RequestParam(name = "username",required = true) String username, @RequestParam(name = "roleName",required = true) String roleName){
        return ResponseEntity.ok().body(userService.removeRoleFromUser(username, roleName).getRoles());
    }
}
