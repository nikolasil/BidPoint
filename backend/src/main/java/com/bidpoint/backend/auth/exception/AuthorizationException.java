package com.bidpoint.backend.auth.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class AuthorizationException extends ResponseStatusException {
    public AuthorizationException(Exception e) {
        super(HttpStatus.FORBIDDEN,"Something went wrong during Sign In", e);
    }
}
