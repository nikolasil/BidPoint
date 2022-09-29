package com.bidpoint.backend.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UserAlreadyExistsException extends ResponseStatusException {
    public UserAlreadyExistsException(String str) {
        super(HttpStatus.FORBIDDEN, "User with username=" + str + "  already exists");
    }
}
