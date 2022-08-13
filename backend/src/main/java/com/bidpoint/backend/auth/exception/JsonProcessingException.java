package com.bidpoint.backend.auth.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class JsonProcessingException extends ResponseStatusException {
    public JsonProcessingException(Exception e) {
        super(HttpStatus.BAD_REQUEST,"Something went wrong during json processing", e);
    }
}
