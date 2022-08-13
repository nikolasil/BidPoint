package com.bidpoint.backend.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UserNotApprovedException extends ResponseStatusException {
    public UserNotApprovedException(String str) {
        super(HttpStatus.FORBIDDEN, "User with username=" + str + " is not approved");
    }
}
