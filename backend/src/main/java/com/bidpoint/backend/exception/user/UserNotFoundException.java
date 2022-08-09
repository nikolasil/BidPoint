package com.bidpoint.backend.exception.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UserNotFoundException extends ResponseStatusException {
    public UserNotFoundException(String str) {
        super(HttpStatus.BAD_REQUEST, "User with username=" + str + "  not found");
    }
}
