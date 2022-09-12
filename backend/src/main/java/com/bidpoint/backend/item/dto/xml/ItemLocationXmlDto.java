package com.bidpoint.backend.item.dto.xml;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;
import lombok.*;

import javax.persistence.Lob;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ItemLocationXmlDto {
    @JacksonXmlProperty(localName = "Latitude", isAttribute = true)
    private String latitude;
    @JacksonXmlProperty(localName = "Longitude", isAttribute = true)
    private String longitude;
    @JacksonXmlText
    private String location;
}
