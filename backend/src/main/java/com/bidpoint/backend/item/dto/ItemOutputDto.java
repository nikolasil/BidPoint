package com.bidpoint.backend.item.dto;

import com.bidpoint.backend.item.entity.Bid;
import com.bidpoint.backend.item.entity.Image;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
public class ItemOutputDto {
    private UUID id;

    private String name;

    private String description;

    private BigDecimal startingPrice;

    private BigDecimal currentPrice;

    private BigDecimal buyPrice;

    private Integer numberOfBids;

    private boolean active;

    private ZonedDateTime dateEnds;

    private ZonedDateTime dateCreated;

    private ZonedDateTime dateUpdated;

    private String username;

    private Set<String> categories;

    private Set<Image> images;
}