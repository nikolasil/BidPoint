package com.bidpoint.backend.item.dto.xml;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.*;

import javax.persistence.Lob;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ItemImageXmlDto {
    @JacksonXmlProperty(localName = "FileName")
    private String fileName;
    @JacksonXmlProperty(localName = "FileType")
    private String fileType;

    @JacksonXmlProperty(localName = "FileData")
    @Lob
    private byte[] fileData;
}
