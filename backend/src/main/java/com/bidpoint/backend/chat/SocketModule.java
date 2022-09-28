package com.bidpoint.backend.chat;

import com.bidpoint.backend.chat.entity.Message;
import com.bidpoint.backend.chat.service.SocketService;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Slf4j
public class SocketModule {
    private final SocketService socketService;

    public SocketModule(SocketIOServer server, SocketService socketService) {
        this.socketService = socketService;
        server.addConnectListener(onConnected());
        server.addDisconnectListener(onDisconnected());
        server.addEventListener("send_message", Message.class, onChatReceived());
    }

    private DataListener<Message> onChatReceived() {
        return (senderClient, data, ackSender) -> {
            log.info(data.toString());
            socketService.saveMessage(senderClient, data);
        };
    }

    private ConnectListener onConnected() {
        return (client) -> {
            Map<String, List<String>> params = client.getHandshakeData().getUrlParams();
            if(params.containsKey("room") && params.containsKey("username")){
                String room = params.get("room").stream().collect(Collectors.joining());
                String username = params.get("username").stream().collect(Collectors.joining());

                client.joinRoom(room);
                socketService.saveInfoMessage(client, username + " connected", room);
                log.info("Connected Socket ID[{}] - room[{}] - username [{}]", client.getSessionId().toString(), room, username);
            }
        };

    }

    private DisconnectListener onDisconnected() {
        return client -> {
            Map<String, List<String>> params = client.getHandshakeData().getUrlParams();
            if(params.containsKey("room") && params.containsKey("username")) {
                String room = params.get("room").stream().collect(Collectors.joining());
                String username = params.get("username").stream().collect(Collectors.joining());

                socketService.saveInfoMessage(client, username + " disconnected", room);
                log.info("Disconnected Socket ID[{}] - room[{}] - username [{}]", client.getSessionId().toString(), room, username);
            }
        };
    }


}
