package com.bidpoint.backend.item.dto;

import com.bidpoint.backend.item.entity.Image;
import com.bidpoint.backend.item.entity.Item;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
public class PageItemsOutputDto {
    private Long totalItems;
    private List<ItemOutputDto> items;
}
