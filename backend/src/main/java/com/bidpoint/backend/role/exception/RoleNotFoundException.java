package com.bidpoint.backend.role.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class RoleNotFoundException extends ResponseStatusException {
    public RoleNotFoundException(String str) {
        super(HttpStatus.BAD_REQUEST, "Role with name=" + str + " not found");
    }
}
