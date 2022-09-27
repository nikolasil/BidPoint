package com.bidpoint.backend.item.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;

public class BidAmountException extends ResponseStatusException {
    public BidAmountException(BigDecimal amount, BigDecimal currentAmount, Long itemId) {
        super(HttpStatus.BAD_REQUEST, "Item: " + itemId + ", amount: " + amount + ", currentAmount: "+currentAmount +" bid amount must be bigger that current amount");
    }
}
