package com.bidpoint.backend.item.converter;

import com.bidpoint.backend.item.dto.ItemInputDto;
import com.bidpoint.backend.item.dto.ItemOutputDto;
import com.bidpoint.backend.item.entity.Item;
import org.springframework.core.convert.converter.Converter;

import java.util.LinkedHashSet;

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
                source.getBids(),
                source.getCategory().getName(),
                source.isActive(),
                source.getDateEnds(),
                source.getDateCreated(),
                source.getDateUpdated()
        );
    }
}
