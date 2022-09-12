package com.bidpoint.backend.item.converter.xml;
import com.bidpoint.backend.item.dto.xml.*;
import com.bidpoint.backend.item.entity.Category;
import com.bidpoint.backend.item.entity.Item;
import org.springframework.core.convert.converter.Converter;

import java.util.ArrayList;
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
                        bid.getDateCreated().toString(),
                        bid.getAmount().toString()
                )).toList(),
                new ItemLocationXmlDto("","",""),
                "",
                source.getDateCreated().toString(),
                source.getDateEnds().toString(),
                new ItemSellerXmlDto(source.getUser().getUsername(),""),
                source.getDescription(),
                new ArrayList<>()
        );
    }
}
