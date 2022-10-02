package com.bidpoint.backend.item.dto.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ItemBidderXmlDto {
    @JacksonXmlProperty(isAttribute = true, localName = "Rating")
    private String rating;
    @JacksonXmlProperty(isAttribute = true, localName = "UserID")
    private String username;
    @JacksonXmlProperty(localName = "Location")
    private String location;
    @JacksonXmlProperty(localName = "Country")
    private String country;
}
