package com.bidpoint.backend.item.converter;

import com.bidpoint.backend.item.dto.ItemOutputDto;
import com.bidpoint.backend.item.entity.Category;
import com.bidpoint.backend.item.entity.Item;
import org.springframework.core.convert.converter.Converter;

import java.util.stream.Collectors;

public class ItemConverter implements Converter<Item, ItemOutputDto> {
    @Override
    public ItemOutputDto convert(Item source) {
        return new ItemOutputDto(
                source.getId(),
                source.getName(),
                source.getDescription(),
                source.getStartingPrice(),
                source.getCurrentPrice(),
                source.getBuyPrice(),
                source.getNumberOfBids(),
                source.getCategories().stream().map(Category::getName).collect(Collectors.toSet()),
                source.getImages(),
                source.isActive(),
                source.getDateEnds(),
                source.getDateCreated(),
                source.getDateUpdated()
        );
    }
}
