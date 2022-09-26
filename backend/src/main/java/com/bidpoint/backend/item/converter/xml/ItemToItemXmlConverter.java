package com.bidpoint.backend.item.converter.xml;

import com.bidpoint.backend.item.dto.xml.*;
import com.bidpoint.backend.item.entity.Category;
import com.bidpoint.backend.item.entity.Item;
import org.springframework.core.convert.converter.Converter;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.stream.Collectors;

public class ItemToItemXmlConverter implements Converter<Item, ItemXmlDto> {
    @Override
    public ItemXmlDto convert(Item source) {
        return new ItemXmlDto(
                source.getId().toString(),
                source.getName(),
                source.getCategories().stream().map(Category::getName).collect(Collectors.toList()),
                source.getCurrentPrice().toString(),
                source.getStartingPrice().toString(),
                source.getBuyPrice().toString(),
                source.getNumberOfBids().toString(),
                source.getBids().stream().map(bid-> new ItemBidXmlDto(
                        new ItemBidderXmlDto(
                                bid.getUser().getUsername(),
                                bid.getUser().getUsername(),
                                bid.getUser().getAddress(),
                                bid.getUser().getAddress()
                        ),
                        bid.getDateCreated().format(DateTimeFormatter.ofPattern("MMM-dd-yy' 'HH:mm:ss").localizedBy(Locale.ENGLISH)),
                        bid.getAmount().toString()
                )).toList(),
                new ItemLocationXmlDto("","",""),
                "",
                source.getDateCreated().format(DateTimeFormatter.ofPattern("MMM-dd-yy' 'HH:mm:ss").localizedBy(Locale.ENGLISH)),
                source.getDateEnds().format(DateTimeFormatter.ofPattern("MMM-dd-yy' 'HH:mm:ss").localizedBy(Locale.ENGLISH)),
                new ItemSellerXmlDto(source.getUser().getUsername(),""),
                source.getDescription(),
                new ArrayList<>()
        );
    }
}
