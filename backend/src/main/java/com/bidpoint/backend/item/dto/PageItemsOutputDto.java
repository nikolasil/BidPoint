package com.bidpoint.backend.item.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PageItemsOutputDto {
    private Long totalItems;
    private List<ItemOutputDto> items;
    private SearchItemStateOutputDto searchState;
}
