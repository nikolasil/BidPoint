package com.bidpoint.backend.item.dto.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ItemBidXmlDto {
    @JacksonXmlProperty(localName = "Bidder")
    private ItemBidderXmlDto bidder;
    @JacksonXmlProperty(localName = "Time")
    private String time;
    @JacksonXmlProperty(localName = "Amount")
    private String amount;
}
