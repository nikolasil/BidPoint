package com.bidpoint.backend.chat.service.impl;

import com.bidpoint.backend.chat.entity.Message;
import com.bidpoint.backend.chat.repository.MessageRepository;
import com.bidpoint.backend.chat.service.MessageService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;

    @Override
    public List<Message> getMessagesReceiver(String receiver) {
        return messageRepository.findAllByReceiver(receiver);
    }

    @Override
    public List<Message> getMessagesSender(String sender) {
        return messageRepository.findAllBySender(sender);
    }
    @Override
    public List<Message> getMessagesReceiverAndSender(String receiver, String sender) {
        return messageRepository.findAllBySenderAndReceiver(sender, receiver);
    }

    @Override
    public Message saveMessage(Message message) {
        return messageRepository.save(message);
    }
}
