package com.bidpoint.backend.auth.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class TokenIsMissingException extends ResponseStatusException {
    public TokenIsMissingException() {
        super(HttpStatus.BAD_REQUEST,"Refresh token must be provided");
    }
}
