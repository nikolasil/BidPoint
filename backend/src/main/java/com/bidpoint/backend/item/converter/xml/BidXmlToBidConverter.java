package com.bidpoint.backend.item.converter.xml;

import com.bidpoint.backend.item.dto.xml.ItemBidXmlDto;
import com.bidpoint.backend.item.entity.Bid;
import com.bidpoint.backend.user.entity.User;
import org.springframework.core.convert.converter.Converter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static java.time.ZoneOffset.UTC;

public class BidXmlToBidConverter implements Converter<ItemBidXmlDto, Bid> {

    @Override
    public Bid convert(ItemBidXmlDto source) {
        String date = source.getTime();
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
        return new Bid(
                null,
                new BigDecimal(source.getAmount().substring(1)),
                zonedDate,
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
                        null
                )
        );
    }
}
