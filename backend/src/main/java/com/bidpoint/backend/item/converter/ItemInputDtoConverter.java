package com.bidpoint.backend.item.converter;

import com.bidpoint.backend.item.dto.ItemInputDto;
import com.bidpoint.backend.item.entity.Item;
import com.bidpoint.backend.item.repository.CategoryRepository;
import org.springframework.core.convert.converter.Converter;

import java.util.LinkedHashSet;

public class ItemInputDtoConverter implements Converter<ItemInputDto, Item> {
    @Override
    public Item convert(ItemInputDto source) {
        return new Item(
                null,
                source.getName(),
                source.getDescription(),
                source.getStartingPrice(),
                source.getStartingPrice(),
                source.getBuyPrice(),
                0,
                new LinkedHashSet<>(),
                null,
                source.isActive(),
                source.getDateEnds(),
                null,
                null
        );
    }
}