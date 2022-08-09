package com.bidpoint.backend.exception.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UserAlreadyExistsException extends ResponseStatusException {
    public UserAlreadyExistsException(String str) {
        super(HttpStatus.ALREADY_REPORTED, "User with username=" + str + "  already exists");
    }
}
