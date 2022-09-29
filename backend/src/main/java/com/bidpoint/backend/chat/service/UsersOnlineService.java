package com.bidpoint.backend.chat.service;

import com.bidpoint.backend.chat.entity.UsersOnline;

import java.util.List;
import java.util.UUID;

public interface UsersOnlineService {
    List<UsersOnline> getAll();

    void addOnlineUser(String username, UUID sessionId);

    void removeOnlineUser(UUID sessionId);

    UsersOnline getOnlineUser(UUID sessionId);

    UsersOnline getOnlineUser(String username);
}
