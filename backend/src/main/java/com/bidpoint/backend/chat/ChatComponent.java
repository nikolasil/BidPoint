package com.bidpoint.backend.chat;

import com.bidpoint.backend.chat.entity.Message;
import com.bidpoint.backend.chat.entity.MessageOutputDto;
import com.bidpoint.backend.chat.entity.UsersOnline;
import com.bidpoint.backend.chat.service.impl.MessageServiceImpl;
import com.bidpoint.backend.chat.service.impl.UsersOnlineServiceImpl;
import com.bidpoint.backend.user.service.UserServiceImpl;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Slf4j
public class ChatComponent {
    private final SocketIOServer server;
    private final UsersOnlineServiceImpl usersOnlineService;
    private final UserServiceImpl userService;
    private final MessageServiceImpl messageService;
    private final ConversionService conversionService;

    public ChatComponent(SocketIOServer server, UsersOnlineServiceImpl usersOnlineService, UserServiceImpl userService, MessageServiceImpl messageService, ConversionService conversionService) {
        this.server = server;
        this.usersOnlineService = usersOnlineService;
        this.userService = userService;
        this.messageService = messageService;
        this.conversionService = conversionService;
        this.server.addConnectListener(onConnected());
        this.server.addDisconnectListener(onDisconnected());
        this.server.addEventListener("send_message", Message.class, onSendMessage());
    }

    private DataListener<Message> onSendMessage() {
        return (senderClient, data, ackSender) -> {
            Message storedMessage = messageService.saveMessage(data);
            UsersOnline receiver = usersOnlineService.getOnlineUser(data.getReceiver());
            senderClient.sendEvent("receive_message", conversionService.convert(storedMessage, MessageOutputDto.class));
            if(receiver != null)
                this.server.getClient(receiver.getSessionId()).sendEvent("receive_message", conversionService.convert(storedMessage, MessageOutputDto.class));
        };
    }

    private ConnectListener onConnected() {
        return (client) -> {
            Map<String, List<String>> params = client.getHandshakeData().getUrlParams();
            if(params.containsKey("username")){
                String username = params.get("username").stream().collect(Collectors.joining());
                if(userService.getUser(username) == null){
                    client.disconnect();
                    return;
                };

                client.joinRoom("chats");

                log.info("Connected Socket ID[{}] - username [{}]", client.getSessionId().toString(), username);
                usersOnlineService.addOnlineUser(username, client.getSessionId());
                this.server.getBroadcastOperations().sendEvent(
                        "users_online",
                        usersOnlineService.getAll());
            }
        };

    }

    private DisconnectListener onDisconnected() {
        return (client) -> {
            Map<String, List<String>> params = client.getHandshakeData().getUrlParams();
            if(params.containsKey("username")) {
                String username = params.get("username").stream().collect(Collectors.joining());

                log.info("Disconnected Socket ID[{}] - username [{}]", client.getSessionId().toString(), username);
                usersOnlineService.removeOnlineUser(client.getSessionId());
                this.server.getBroadcastOperations().sendEvent(
                        "users_online",
                        usersOnlineService.getAll());
            }
        };
    }


}
