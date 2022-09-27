package com.bidpoint.backend.item.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
public class BidOutputDto {
    private Long id;
    private BigDecimal amount;
    private String username;

    private ZonedDateTime dateCreated;
}
