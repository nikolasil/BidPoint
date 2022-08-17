package com.bidpoint.backend.item.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class CategoryInputDto {
    private String name;
    private String description;
}
