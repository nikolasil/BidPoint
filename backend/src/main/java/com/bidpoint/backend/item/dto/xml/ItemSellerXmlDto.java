package com.bidpoint.backend.item.dto.xml;

import com.bidpoint.backend.item.entity.Bid;
import com.bidpoint.backend.item.entity.Image;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ItemSellerXmlDto {
    @JacksonXmlProperty(isAttribute = true, localName = "Rating")
    private String rating;
    @JacksonXmlProperty(isAttribute = true, localName = "UserID")
    private String username;
}
