package com.bidpoint.backend.item.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ItemNotActiveException extends ResponseStatusException {
    public ItemNotActiveException(String str) {
        super(HttpStatus.NOT_FOUND, "Item: " + str + "  not active");
    }
}
