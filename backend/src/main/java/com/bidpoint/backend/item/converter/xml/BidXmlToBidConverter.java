package com.bidpoint.backend.item.converter.xml;

import com.bidpoint.backend.item.dto.xml.ItemBidXmlDto;
import com.bidpoint.backend.item.entity.Bid;
import com.bidpoint.backend.user.entity.User;
import org.springframework.core.convert.converter.Converter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static java.time.ZoneOffset.UTC;

public class BidXmlToBidConverter implements Converter<ItemBidXmlDto, Bid> {

    @Override
    public Bid convert(ItemBidXmlDto source) {
        return new Bid(
                null,
                source.getAmount().contains("$") ? new BigDecimal(source.getAmount().substring(1)) : new BigDecimal(source.getAmount()),
                LocalDateTime.parse( source.getTime()+".000", DateTimeFormatter.ofPattern("MMM-dd-yy' 'HH:mm:ss.SSS").localizedBy(Locale.ENGLISH)).atZone(UTC),
                null,
                new User(
                        null,
                        "",
                        "",
                        source.getBidder().getUsername(),
                        "1234",
                        true,
                        "",
                        "",
                        "",
                        "",
                        null,
                        null,
                        null,
                        null,
                        Long.valueOf(0),
                        Long.valueOf(0)
                )
        );
    }
}
