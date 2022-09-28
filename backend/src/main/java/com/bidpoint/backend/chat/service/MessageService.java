package com.bidpoint.backend.chat.service;

import com.bidpoint.backend.chat.entity.Message;

import java.util.List;

public interface MessageService {
    List<Message> getMessages(String room) ;
    Message saveMessage(Message message) ;
}
