package com.bidpoint.backend.chat.service;

import com.bidpoint.backend.chat.entity.Message;
import com.corundumstudio.socketio.SocketIOClient;

public interface SocketService {

    void sendSocketMessage(SocketIOClient senderClient, Message message, String room);
    void saveMessage(SocketIOClient senderClient, Message message);
    void saveInfoMessage(SocketIOClient senderClient, String message, String room);
}
