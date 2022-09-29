package com.bidpoint.backend.chat.repository;

import com.bidpoint.backend.chat.entity.UsersOnline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UsersOnlineRepository extends JpaRepository<UsersOnline, Long> {
    void deleteBySessionId(UUID sessionId);
    UsersOnline findBySessionId(UUID sessionId);
    UsersOnline findByUsername(String username);
}
