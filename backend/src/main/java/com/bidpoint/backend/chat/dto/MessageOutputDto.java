package com.bidpoint.backend.chat.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MessageOutputDto {
    private String dateCreated;
    private String content;
    private String sender;
    private String receiver;
}
