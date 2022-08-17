package com.bidpoint.backend.item.repository;

import com.bidpoint.backend.item.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item,Long> {
    Item findItemById(Long id);

    @Query( "SELECT i FROM Item i WHERE " +
            "i.name LIKE CONCAT('%', :searchTerm, '%')" +
            "OR i.description LIKE CONCAT('%', :searchTerm, '%')")
    List<Item> searchItems(String searchTerm);
}
