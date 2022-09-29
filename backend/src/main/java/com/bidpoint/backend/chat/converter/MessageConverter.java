package com.bidpoint.backend.chat.converter;

import com.bidpoint.backend.chat.entity.Message;
import com.bidpoint.backend.chat.entity.MessageOutputDto;
import org.springframework.core.convert.converter.Converter;

public class MessageConverter implements Converter<Message, MessageOutputDto> {
    @Override
    public MessageOutputDto convert(Message source) {
        return new MessageOutputDto(
                source.getDateCreated().toString(),
                source.getContent(),
                source.getSender(),
                source.getReceiver()
        );
    }
}
