package com.bidpoint.backend.item.dto;

import com.bidpoint.backend.item.entity.Image;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
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

    private ZonedDateTime dateEnds;
}
