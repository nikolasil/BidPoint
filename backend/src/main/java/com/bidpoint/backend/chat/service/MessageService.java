package com.bidpoint.backend.chat.service;

import com.bidpoint.backend.chat.entity.Message;

import java.util.List;

public interface MessageService {
    List<Message> getMessagesReceiver(String receiver) ;

    List<Message> getMessagesSender(String sender);

    List<Message> getMessagesReceiverAndSender(String receiver, String sender);

    Message saveMessage(Message message) ;
}
