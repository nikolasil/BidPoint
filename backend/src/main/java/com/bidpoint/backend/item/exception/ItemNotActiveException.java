package com.bidpoint.backend.item.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ItemHasEndedException extends ResponseStatusException {
    public ItemHasEndedException(String str) {
        super(HttpStatus.NOT_FOUND, "Item: " + str + "  has ended");
    }
}
