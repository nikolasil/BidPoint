package com.bidpoint.backend.item.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
public class ItemInputDto {
    private String name;
    private String description;
    private BigDecimal startingPrice;
    private BigDecimal buyPrice;
    private Set<String> categories;
    private boolean isActive;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy' 'HH:mm:ss")
    private LocalDateTime dateEnds;
}
