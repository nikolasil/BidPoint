package com.bidpoint.backend.item.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CategoryNotFoundException extends ResponseStatusException {
    public CategoryNotFoundException(String str) {
        super(HttpStatus.NOT_FOUND, "Category: " + str + "  not found");
    }
}
