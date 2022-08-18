package com.bidpoint.backend.auth.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UserNotApprovedException extends ResponseStatusException {
    public UserNotApprovedException(String str) {
        super(HttpStatus.FORBIDDEN, "User with username=" + str + " is not approved");
    }
}
