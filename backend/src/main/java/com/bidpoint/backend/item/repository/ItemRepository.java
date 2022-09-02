package com.bidpoint.backend.item.repository;

import com.bidpoint.backend.item.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface ItemRepository extends JpaRepository<Item,Long> {
    Item findItemById(Long id);

    Page<Item> findAllByActive(boolean active, Pageable pageable);
    Page<Item> findAllByDateEndsAfter(LocalDateTime min, Pageable pageable);

    Long countAllByActive(boolean active);

    @Query( "SELECT i FROM Item i WHERE " +
            "i.name LIKE CONCAT('%', :searchTerm, '%')" +
            "OR i.description LIKE CONCAT('%', :searchTerm, '%')")
    List<Item> searchItems(String searchTerm);
    @Query( "SELECT i FROM Item i WHERE " +
            "i.active = :active AND (" +
            "i.name LIKE CONCAT('%', :searchTerm, '%')" +
            "OR i.description LIKE CONCAT('%', :searchTerm, '%'))")
    List<Item> searchItemsByActive(boolean active, String searchTerm);
}
