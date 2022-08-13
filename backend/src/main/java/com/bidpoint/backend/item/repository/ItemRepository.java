package com.bidpoint.backend.item.repository;

import com.bidpoint.backend.user.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item,Long> {
    Item findItemById(Long id);
}
