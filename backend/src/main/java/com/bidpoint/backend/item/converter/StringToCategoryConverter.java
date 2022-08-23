package com.bidpoint.backend.item.converter;

import com.bidpoint.backend.item.entity.Category;
import org.springframework.core.convert.converter.Converter;

import java.util.LinkedHashSet;

public class StringToCategoryConverter implements Converter<String, Category> {
    @Override
    public Category convert(String source) {
        return new Category(
                null,
                source,
                new LinkedHashSet<>()
        );
    }
}
