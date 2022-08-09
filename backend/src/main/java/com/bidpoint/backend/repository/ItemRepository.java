package com.bidpoint.backend.repository;

import com.bidpoint.backend.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item,Long> {
    Item findItemById(Long id);
}
