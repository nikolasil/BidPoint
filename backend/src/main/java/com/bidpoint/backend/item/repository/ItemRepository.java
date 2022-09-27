package com.bidpoint.backend.item.repository;

import com.bidpoint.backend.item.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item,Long>, ItemRepositoryCustom {
    Item findItemById(Long id);
}
