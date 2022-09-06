package com.bidpoint.backend.item.repository;

import com.bidpoint.backend.item.entity.Item;
import com.bidpoint.backend.user.entity.User;
import org.apache.tomcat.jni.Local;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ItemRepository extends JpaRepository<Item,Long> {
    Item findItemById(Long id);

    Page<Item> findAllByActive(boolean active, Pageable pageable);
    Page<Item> findAllByUser(User user, Pageable pageable);
    Page<Item> findAllByDateEndsAfter(LocalDateTime min, Pageable pageable);

    Page<Item> findAllByActiveAndUser(boolean active, User user, Pageable pageable);
    Page<Item> findAllByActiveAndDateEndsAfter(boolean active,LocalDateTime min, Pageable pageable);
    Page<Item> findAllByUserAndDateEndsAfter(User user, LocalDateTime min, Pageable pageable);

    Page<Item> findAllByActiveAndDateEndsAfterAndUser(boolean active, LocalDateTime min, User user, Pageable pageable);

    Long countAllByActive(boolean active);
    Long countAllByUser(User user);
    Long countAllByDateEndsAfter(LocalDateTime min);

    Long countAllByActiveAndUser(boolean active, User user);
    Long countAllByActiveAndDateCreatedAfter(boolean active, LocalDateTime min);
    Long countAllByUserAndDateCreatedAfter(User user, LocalDateTime min);

    Long countAllByActiveAndDateCreatedAfterAndUser(boolean active, LocalDateTime min, User user);

    @Query( "SELECT i FROM Item i WHERE " +
            "i.name LIKE CONCAT('%', :searchTerm, '%')" +
            "OR i.description LIKE CONCAT('%', :searchTerm, '%')")
    Page<Item> searchItems(String searchTerm, Pageable pageable);
    @Query( value = "SELECT i FROM Item i WHERE " +
            "i.active = :active AND (" +
            "i.name LIKE CONCAT('%', :searchTerm, '%')" +
            "OR i.description LIKE CONCAT('%', :searchTerm, '%'))")
    Page<Item> searchItemsByActive(boolean active, String searchTerm, Pageable pageable);
}
