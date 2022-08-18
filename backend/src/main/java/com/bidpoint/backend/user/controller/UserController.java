package com.bidpoint.backend.user.controller;

import com.bidpoint.backend.auth.service.AuthServiceImpl;
import com.bidpoint.backend.user.dto.UserInputDto;
import com.bidpoint.backend.user.dto.UserOutputDto;
import com.bidpoint.backend.user.entity.User;
import com.bidpoint.backend.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Log4j2
public class UserController {

    private final UserService userService;
    private final ConversionService conversionService;
    private final AuthServiceImpl authService;

    @GetMapping()
    public ResponseEntity<UserOutputDto> getUserWithUsername(@RequestParam(name = "username") String username){
        User user = userService.getUser(username);
        return ResponseEntity.status(HttpStatus.OK).body(conversionService.convert(user, UserOutputDto.class));
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserOutputDto>> getAllUsers(){
        List<User> listOfUsers = userService.getUsers();
        return ResponseEntity.status(HttpStatus.OK).body(listOfUsers.stream().map(user->conversionService.convert(user,UserOutputDto.class)).collect(Collectors.toList()));
    }

    @GetMapping("/approved")
    public ResponseEntity<List<UserOutputDto>> getNotApprovedUsers(@RequestParam(name = "value") boolean value){
        List<User> listOfUsers = userService.getUserByApproved(value);
        return ResponseEntity.status(HttpStatus.OK).body(listOfUsers.stream().map(user->conversionService.convert(user,UserOutputDto.class)).collect(Collectors.toList()));
    }

    @PostMapping
    public ResponseEntity<UserOutputDto> createNewUser(@Valid @RequestBody UserInputDto userInputDto) {
        User newUser = userService.createUser(conversionService.convert(userInputDto, User.class), Arrays.asList("seller", "bidder"));
        return ResponseEntity.status(HttpStatus.CREATED).body(conversionService.convert(newUser,UserOutputDto.class));
    }

    @GetMapping("/me")
    public ResponseEntity<UserOutputDto> getUser(HttpServletRequest request) {
        String username = authService.decodeAuthorizationHeader(request.getHeader(AUTHORIZATION)).getSubject();
        User user = userService.getUser(username);
        return ResponseEntity.status(HttpStatus.OK).body(conversionService.convert(user,UserOutputDto.class));
    }

    @PutMapping("/approve")
    public ResponseEntity<UserOutputDto> approveUser(@RequestParam(name = "username") String username) {
        User approvedUser = userService.approveUser(username);
        return ResponseEntity.status(HttpStatus.OK).body(conversionService.convert(approvedUser,UserOutputDto.class));
    }

    @PutMapping("/role")
    public ResponseEntity<UserOutputDto>addRoleToUser(@RequestParam(name = "username") String username, @RequestParam(name = "roleName") String roleName){
        User updatedUser = userService.addRoleToUser(username, roleName);
        return ResponseEntity.status(HttpStatus.OK).body(conversionService.convert(updatedUser,UserOutputDto.class));
    }

    @DeleteMapping("/role")
    public ResponseEntity<UserOutputDto>removeRoleFromUser(@RequestParam(name = "username") String username, @RequestParam(name = "roleName") String roleName){
        User updatedUser = userService.removeRoleFromUser(username, roleName);
        return ResponseEntity.status(HttpStatus.OK).body(conversionService.convert(updatedUser,UserOutputDto.class));
    }
}
