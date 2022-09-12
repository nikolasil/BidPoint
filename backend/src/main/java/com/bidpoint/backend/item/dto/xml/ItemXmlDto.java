package com.bidpoint.backend.item.dto.xml;

import com.bidpoint.backend.item.entity.Bid;
import com.bidpoint.backend.item.entity.Image;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ItemXmlDto {
    @JacksonXmlProperty(localName = "ItemID", isAttribute = true)
    private String id;
    @JacksonXmlProperty(localName = "Name")
    private String name;
    @JacksonXmlProperty(localName = "Category")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<String> categories = new ArrayList<>();
    @JacksonXmlProperty(localName = "Currently")
    private String currentPrice;
    @JacksonXmlProperty(localName = "First_Bid")
    private String startingPrice;
    @JacksonXmlProperty(localName = "Buy_Price")
    private String buyPrice;
    @JacksonXmlProperty(localName = "Number_of_Bids")
    private String numberOfBids;
    @JacksonXmlProperty(localName = "Bids")
    private List<ItemBidXmlDto> bids = new ArrayList<>();
    @JacksonXmlProperty(localName = "Location")
    private ItemLocationXmlDto location;
    @JacksonXmlProperty(localName = "Country")
    private String country;
    @JacksonXmlProperty(localName = "Started")
    private String dateCreated;
    @JacksonXmlProperty(localName = "Ends")
    private String dateEnds;
    @JacksonXmlProperty(localName = "Seller")
    private ItemSellerXmlDto seller;
    @JacksonXmlProperty(localName = "Description")
    private String description;
    @JacksonXmlProperty(localName = "Images")
    private List<ItemImageXmlDto> images;
}
