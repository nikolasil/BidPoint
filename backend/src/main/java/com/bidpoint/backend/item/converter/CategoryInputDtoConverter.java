package com.bidpoint.backend.item.converter;

import com.bidpoint.backend.item.dto.CategoryInputDto;
import com.bidpoint.backend.item.dto.ItemInputDto;
import com.bidpoint.backend.item.entity.Category;
import com.bidpoint.backend.item.entity.Item;
import org.springframework.core.convert.converter.Converter;

import java.util.LinkedHashSet;

public class CategoryInputDtoConverter implements Converter<CategoryInputDto, Category> {
    @Override
    public Category convert(CategoryInputDto source) {
        return new Category(
                null,
                source.getName(),
                source.getDescription(),
                new LinkedHashSet<>()
        );
    }
}
