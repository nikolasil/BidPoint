package com.bidpoint.backend.item.service;

import com.bidpoint.backend.item.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.util.List;

public interface ItemService {
    Item createItemWithCategory(Item item, String categoryName);
    Item getItem(Long itemId);
    List<Item> searchItems(String query);
    Page<Item> getItemsPaginationAndSort(int pageNumber, int itemCount, String sortField, Sort.Direction sortDirection);
}
