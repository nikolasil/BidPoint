package com.bidpoint.backend.controller;

import com.bidpoint.backend.dto.user.UserInputDto;
import com.bidpoint.backend.dto.user.UserOutputDto;
import com.bidpoint.backend.entity.User;
import com.bidpoint.backend.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.*;

import static java.util.Arrays.stream;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Log4j2
public class UserController {

    private final UserService userService;

    @GetMapping()
    public ResponseEntity<UserOutputDto> getUserWithUsername(@RequestParam(name = "username") String username){
        UserOutputDto user = userService.getUser(username);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserOutputDto>> getAllUsers(){
        List<UserOutputDto> listOfUsers = userService.getUsers();
        return ResponseEntity.status(HttpStatus.OK).body(listOfUsers);
    }

    @PostMapping
    public ResponseEntity<UserOutputDto> createNewUser(@Valid @RequestBody UserInputDto userInputDto) {
        UserOutputDto newUser = userService.createUser(userInputDto, Arrays.asList("seller", "bidder"));
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    @PutMapping("/approve")
    public ResponseEntity<UserOutputDto> approveUser(@RequestParam(name = "username") String username) {
        UserOutputDto approvedUser = userService.approveUser(username);
        return ResponseEntity.status(HttpStatus.OK).body(approvedUser);
    }

    @PutMapping("/role")
    public ResponseEntity<UserOutputDto>addRoleToUser(@RequestParam(name = "username") String username, @RequestParam(name = "roleName") String roleName){
        UserOutputDto updatedUser = userService.addRoleToUser(username, roleName);
        return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
    }

    @DeleteMapping("/role")
    public ResponseEntity<UserOutputDto>removeRoleFromUser(@RequestParam(name = "username") String username, @RequestParam(name = "roleName") String roleName){
        UserOutputDto updatedUser = userService.removeRoleFromUser(username, roleName);
        return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
    }
}
