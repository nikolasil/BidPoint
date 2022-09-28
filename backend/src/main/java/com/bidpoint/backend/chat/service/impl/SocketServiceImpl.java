package com.bidpoint.backend.chat.service.impl;

import com.bidpoint.backend.chat.entity.Message;
import com.bidpoint.backend.chat.service.SocketService;
import com.bidpoint.backend.enums.MessageType;
import com.corundumstudio.socketio.SocketIOClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class SocketServiceImpl implements SocketService {
    private final MessageServiceImpl messageServiceimpl;

    @Override
    public void sendSocketMessage(SocketIOClient senderClient, Message message, String room) {
        for (SocketIOClient client : senderClient.getNamespace().getRoomOperations(room).getClients())
            if (!client.getSessionId().equals(senderClient.getSessionId()))
                client.sendEvent("receive_message", message);
    }

    @Override
    public void saveMessage(SocketIOClient senderClient, Message message) {
        Message storedMessage = messageServiceimpl.saveMessage(
                new Message(
                        null,
                        null,
                        MessageType.CLIENT,
                        message.getContent(),
                        message.getRoom(),
                        message.getUsername()
                )
        );
        sendSocketMessage(senderClient, storedMessage, message.getRoom());
    }

    @Override
    public void saveInfoMessage(SocketIOClient senderClient, String message, String room) {
        Message storedMessage = messageServiceimpl.saveMessage(
                new Message(
                        null,
                        null,
                        MessageType.SERVER,
                        message,
                        room,
                        ""
                )
        );
        sendSocketMessage(senderClient, storedMessage, room);
    }
}
