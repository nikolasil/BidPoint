package com.bidpoint.backend.chat.service.impl;

import com.bidpoint.backend.chat.entity.UsersOnline;
import com.bidpoint.backend.chat.repository.UsersOnlineRepository;
import com.bidpoint.backend.chat.service.UsersOnlineService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class UsersOnlineServiceImpl implements UsersOnlineService {
    private final UsersOnlineRepository usersOnlineRepository;

    @Override
    public List<UsersOnline> getAll(){
        return usersOnlineRepository.findAll();
    }

    @Override
    public void addOnlineUser(String username, UUID sessionId){
        usersOnlineRepository.save(new UsersOnline(null, username, sessionId));
    }

    @Override
    public void removeOnlineUser(UUID sessionId){
        usersOnlineRepository.deleteBySessionId(sessionId);
    }

    @Override
    public UsersOnline getOnlineUser(UUID sessionId){
        return usersOnlineRepository.findBySessionId(sessionId);
    }

    @Override
    public UsersOnline getOnlineUser(String username){
        return usersOnlineRepository.findByUsername(username);
    }
}
