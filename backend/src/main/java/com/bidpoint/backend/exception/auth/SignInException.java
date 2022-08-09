package com.bidpoint.backend.exception.auth;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class SignInException extends ResponseStatusException {
    public SignInException(Exception e) {
        super(HttpStatus.FORBIDDEN,"Something went wrong during Sign In", e);
    }
}
