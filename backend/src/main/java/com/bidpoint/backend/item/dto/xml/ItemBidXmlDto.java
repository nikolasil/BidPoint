package com.bidpoint.backend.item.dto.xml;

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
public class ItemBidXmlDto {
    @JacksonXmlProperty(localName = "Bidder")
    private ItemBidderXmlDto bidder;
    @JacksonXmlProperty(localName = "Time")
    private String time;
    @JacksonXmlProperty(localName = "Amount")
    private String amount;
}
