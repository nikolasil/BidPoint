package com.bidpoint.backend.item.converter.xml;

import com.bidpoint.backend.item.dto.xml.ItemXmlDto;
import com.bidpoint.backend.item.entity.Item;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashSet;
import java.util.Locale;

import static java.time.ZoneOffset.UTC;
@Slf4j
public class ItemXmlToItemConverter implements Converter<ItemXmlDto, Item> {
    @Override
    public Item convert(ItemXmlDto source) {
        return new Item(
                null,
                source.getName(),
                source.getDescription(),
                new BigDecimal(source.getStartingPrice().substring(1)),
                new BigDecimal(source.getStartingPrice().substring(1)),
                source.getBuyPrice() != null ? new BigDecimal(source.getBuyPrice().substring(1)) : BigDecimal.valueOf(0),
                Integer.parseInt(source.getNumberOfBids()),
                true,
                LocalDateTime.parse( source.getDateEnds()+".000", DateTimeFormatter.ofPattern("MMM-dd-yy' 'HH:mm:ss.SSS").localizedBy(Locale.ENGLISH)).atZone(UTC),
                null,
                null,
                null,
                new LinkedHashSet<>(),
                new LinkedHashSet<>(),
                new LinkedHashSet<>()
        );
    }
}
