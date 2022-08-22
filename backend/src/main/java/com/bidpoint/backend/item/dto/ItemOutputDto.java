package com.bidpoint.backend.item.dto;

import com.bidpoint.backend.item.entity.Bid;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
public class ItemOutputDto {
    private Long id;
    private String name;
    private String description;

    private BigDecimal startingPrice;
    private BigDecimal currentPrice;
    private BigDecimal buyPrice;

    private Integer numberOfBids;

    private Set<Bid> bids;
    private Set<String> categories;

    private boolean isActive;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime dateEnds;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime dateCreated;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime dateUpdated;
}
