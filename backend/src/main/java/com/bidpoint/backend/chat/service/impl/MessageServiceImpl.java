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
    public List<Message> getMessages(String room) {
        return messageRepository.findAllByRoom(room);
    }

    @Override
    public Message saveMessage(Message message) {
        return messageRepository.save(message);
    }
}
