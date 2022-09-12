package com.bidpoint.backend.item.dto.xml;

import com.bidpoint.backend.item.entity.Item;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ItemImageXmlDto {
    @JacksonXmlProperty(localName = "FileName")
    private String fileName;
    @JacksonXmlProperty(localName = "FileType")
    private String fileType;

    @JacksonXmlProperty(localName = "FileData")
    @Lob
    private byte[] fileData;
}
