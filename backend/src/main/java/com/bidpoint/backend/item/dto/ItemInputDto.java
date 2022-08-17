package com.bidpoint.backend.item.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ItemInputDto {
    private String name;
    private String description;
    private BigDecimal startingPrice;
    private BigDecimal buyPrice;
    private String categoryName;
    private boolean isActive;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime dateEnds;
}
