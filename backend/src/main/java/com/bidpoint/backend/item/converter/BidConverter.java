package com.bidpoint.backend.item.converter;

import com.bidpoint.backend.item.dto.BidOutputDto;
import com.bidpoint.backend.item.entity.Bid;
import org.springframework.core.convert.converter.Converter;

import java.math.BigDecimal;

public class BidConverter implements Converter<Bid, BidOutputDto> {
    @Override
    public BidOutputDto convert(Bid source) {
        return new BidOutputDto(
                null,
                source.getAmount(),
                source.getUser().getUsername(),
                source.getDateCreated()
        );
    }
}
