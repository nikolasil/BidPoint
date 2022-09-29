package com.bidpoint.backend.chat.repository;

import com.bidpoint.backend.chat.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findAllByReceiver(String receiver);
    List<Message> findAllBySender(String sender);
    List<Message> findAllBySenderAndReceiver(String sender, String receiver);
}
