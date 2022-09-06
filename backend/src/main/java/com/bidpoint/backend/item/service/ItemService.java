package com.bidpoint.backend.item.service;

import com.bidpoint.backend.item.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ItemService {
    Item createItemWithCategoryAndImages(String username, Item item, List<String> categories, MultipartFile[] images);
    Item getItem(Long itemId);

    Page<Item> getItemsPageable(Optional<Boolean> active, Optional<String> username, Optional<Boolean> dateEnds, int pageNumber, int itemCount, String sortField, Sort.Direction sortDirection);
    Long getItemsCount(Optional<Boolean> active, Optional<String> username, Optional<Boolean> dateEnds);

    Page<Item> getItemsSearchPageable(String searchTerm, Optional<Boolean> active, Optional<String> username, Optional<Boolean> dateEnds, int pageNumber, int itemCount, String sortField, Sort.Direction sortDirection);
    Long getItemsSearchCount(String searchTerm, Optional<Boolean> active, Optional<String> username, Optional<Boolean> dateEnds);
}
