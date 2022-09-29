package com.bidpoint.backend.user.controller;

import com.bidpoint.backend.auth.service.AuthServiceImpl;
import com.bidpoint.backend.enums.FilterMode;
import com.bidpoint.backend.user.dto.*;
import com.bidpoint.backend.user.entity.User;
import com.bidpoint.backend.user.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
@Slf4j
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
    public ResponseEntity<PageUsersOutputDto> getUsersSearchPageableSorting( @RequestParam(name = "searchTerm") String searchTerm,
                                                                 @RequestParam(name = "pageNumber") int pageNumber,
                                                                 @RequestParam(name = "itemCount") int itemCount,
                                                                 @RequestParam(name = "sortField") String sortField,
                                                                 @RequestParam(name = "sortDirection") String sortDirection,
                                                                 @RequestParam(name = "approved") FilterMode approved){
        SearchUserQueryOutputDto results = userService.getUsersSearchPageableSorting(searchTerm,approved,pageNumber,itemCount,sortField, Sort.Direction.fromString(sortDirection));

        List<UserOutputDto> usersList = results.getUsers().stream().map(i -> conversionService.convert(i, UserOutputDto.class)).toList();

        return ResponseEntity.status(HttpStatus.OK).body(
                new PageUsersOutputDto(
                        results.getTotalItems(),
                        usersList,
                        new SearchUserStateOutputDto(pageNumber, itemCount, sortField, sortDirection, searchTerm, approved.toString())
                )
        );
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
