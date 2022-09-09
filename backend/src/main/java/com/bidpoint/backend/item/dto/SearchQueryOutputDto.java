package com.bidpoint.backend.item.dto;

import com.bidpoint.backend.item.entity.Item;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@AllArgsConstructor
public class SearchQueryOutputDto {
    private Page<Item> items;
    private Long totalItems;
}
