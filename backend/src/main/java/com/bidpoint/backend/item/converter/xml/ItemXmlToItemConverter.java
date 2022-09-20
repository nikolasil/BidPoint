package com.bidpoint.backend.item.converter.xml;

import com.bidpoint.backend.item.dto.xml.ItemXmlDto;
import com.bidpoint.backend.item.entity.Item;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashSet;

import static java.time.ZoneOffset.UTC;
@Slf4j
public class ItemXmlToItemConverter implements Converter<ItemXmlDto, Item> {
    @Override
    public Item convert(ItemXmlDto source) {

        String date = source.getDateEnds();
        date = date.replace("Feb","02");
        date = date.replace("Mar","03");
        date = date.replace("Apr","04");
        date = date.replace("May","05");
        date = date.replace("Jun","06");
        date = date.replace("Jul","07");
        date = date.replace("Aug","08");
        date = date.replace("Sep","09");
        date = date.replace("Oct","10");
        date = date.replace("Nov","11");
        date = date.replace("Dec","12");
        date = date.replace(" ","T");
        LocalDateTime dateLocal = LocalDateTime.parse( date+".000", DateTimeFormatter.ofPattern("MM-dd-yy'T'HH:mm:ss.SSS"));
        ZonedDateTime zonedDate = dateLocal.atZone(UTC);

        return new Item(
                null,
                source.getName(),
                source.getDescription(),
                new BigDecimal(source.getStartingPrice().substring(1)),
                new BigDecimal(source.getStartingPrice().substring(1)),
                source.getBuyPrice() != null ? new BigDecimal(source.getBuyPrice().substring(1)) : BigDecimal.valueOf(0),
                Integer.parseInt(source.getNumberOfBids()),
                true,
                zonedDate,
                null,
                null,
                null,
                new LinkedHashSet<>(),
                new LinkedHashSet<>(),
                new LinkedHashSet<>()
        );
    }
}
