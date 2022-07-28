package com.bidpoint.backend.controller;

import com.bidpoint.backend.entity.User;
import com.bidpoint.backend.service.UserService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
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

    @PostMapping
    public ResponseEntity<?> saveUser(@RequestBody User userToBeSaved) {
        userService.saveUser(userToBeSaved);
        userService.addRoleToUser(userToBeSaved.getUsername(), "visitor");
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user").toUriString());
        return ResponseEntity.created(uri).body("User singed up successfully");
    }

    @GetMapping()
    public ResponseEntity<User> getUser(@RequestParam(name = "username") String username){
        return ResponseEntity.ok().body(userService.getUser(username));
    }

    @GetMapping("/me")
    public ResponseEntity<?> getUser(HttpServletRequest request) {

        String authorizationHeader = request.getHeader(AUTHORIZATION);

        String refresh_token = authorizationHeader.substring("Bearer ".length());
        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(refresh_token);
        String username = decodedJWT.getSubject();

        Map<String,Object> response_json = new HashMap<>();
        com.bidpoint.backend.entity.User backendUser = userService.getUser(username);
        Map<String,Object> response_json_user = new HashMap<>();
        response_json.put("user",response_json_user);
        response_json_user.put("username",backendUser.getUsername());
        response_json_user.put("firstname",backendUser.getFirstname());
        response_json_user.put("lastname",backendUser.getLastname());
        response_json_user.put("roles",backendUser.getRoles());

        return ResponseEntity.ok().body(response_json);
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
