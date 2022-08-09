package com.bidpoint.backend.controller;

import com.bidpoint.backend.dto.user.UserInputDto;
import com.bidpoint.backend.dto.user.UserOutputDto;
import com.bidpoint.backend.entity.User;
import com.bidpoint.backend.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.*;

import static java.util.Arrays.stream;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Log4j2
public class UserController {

    private final UserService userService;
    private final ConversionService conversionService;

    @GetMapping()
    public ResponseEntity<UserOutputDto> getUserWithUsername(@RequestParam(name = "username") String username){
        return ResponseEntity.ok().body(conversionService.convert(userService.getUser(username), UserOutputDto.class));
    }

    @PostMapping
    public ResponseEntity<UserOutputDto> createNewUser(@RequestBody UserInputDto userInputDto) {
        User user = conversionService.convert(userInputDto, User.class);

        user = userService.createUser(user, Arrays.asList("seller", "bidder"));

        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user").toUriString());
        return ResponseEntity.created(uri).body(conversionService.convert(userService.getUser(user.getUsername()), UserOutputDto.class));
    }

    @PostMapping("/approve")
    public ResponseEntity<UserOutputDto> approveUser(@RequestParam(name = "username") String username) {
        User user = userService.approveUser(username);
        return ResponseEntity.ok().body(conversionService.convert(user, UserOutputDto.class));
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserOutputDto>> getUsers(){
        List<UserOutputDto> list = userService.getUsers().stream().map(user -> conversionService.convert(user, UserOutputDto.class)).toList();
        return ResponseEntity.ok().body(list);
    }

    @PostMapping("/role")
    public ResponseEntity<UserOutputDto>addRoleToUser(@RequestParam(name = "username") String username, @RequestParam(name = "roleName") String roleName){
        return ResponseEntity.ok().body(conversionService.convert(userService.addRoleToUser(username, roleName), UserOutputDto.class));
    }

    @DeleteMapping("/role")
    public ResponseEntity<UserOutputDto>removeRoleFromUser(@RequestParam(name = "username") String username, @RequestParam(name = "roleName") String roleName){
        return ResponseEntity.ok().body(conversionService.convert(userService.removeRoleFromUser(username, roleName), UserOutputDto.class));
    }
}
