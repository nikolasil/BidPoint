package com.bidpoint.backend.item.converter;

import com.bidpoint.backend.item.entity.Bid;
import org.springframework.core.convert.converter.Converter;

import java.math.BigDecimal;

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
