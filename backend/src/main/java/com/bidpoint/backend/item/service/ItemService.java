package com.bidpoint.backend.item.service;

import com.bidpoint.backend.item.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ItemService {
    Item createItemWithCategoryAndImages(String username, Item item, List<String> categories, MultipartFile[] images);
    Item getItem(Long itemId);
    Long getItemsCount();
    Long getItemsCountByActive(boolean active);
    Page<Item> searchItems(String query, int pageNumber, int itemCount, String sortField, Sort.Direction sortDirection);
    Page<Item> searchItemsByActive(boolean active, String query, int pageNumber, int itemCount, String sortField, Sort.Direction sortDirection);
    Page<Item> getItemsPaginationAndSort(int pageNumber, int itemCount, String sortField, Sort.Direction sortDirection);
    Page<Item> getItemsPaginationAndSortByActive(boolean active, int pageNumber, int itemCount, String sortField, Sort.Direction sortDirection);
}
