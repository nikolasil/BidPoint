package com.bidpoint.backend.item.service;

import com.bidpoint.backend.item.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ItemService {
    Item createItemWithCategoryAndImages(Item item, List<String> categories, MultipartFile[] images);
    Item getItem(Long itemId);
    Long getItemsCount();
    List<Item> searchItems(String query);
    Page<Item> getItemsPaginationAndSort(int pageNumber, int itemCount, String sortField, Sort.Direction sortDirection);
}
