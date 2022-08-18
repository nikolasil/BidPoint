package com.bidpoint.backend.item.converter;

import com.bidpoint.backend.item.dto.CategoryInputDto;
import com.bidpoint.backend.item.entity.Bid;
import com.bidpoint.backend.item.entity.Category;
import org.springframework.core.convert.converter.Converter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;

public class BigDecimalConverter implements Converter<BigDecimal, Bid> {
    @Override
    public Bid convert(BigDecimal source) {
        return new Bid(
                null,
                source,
                null,
                null,
                null
        );
    }
}
