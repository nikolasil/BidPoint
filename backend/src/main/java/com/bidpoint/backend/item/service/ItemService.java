package com.bidpoint.backend.item.service;

import com.bidpoint.backend.item.dto.SearchQueryOutputDto;
import com.bidpoint.backend.item.entity.Item;
import com.bidpoint.backend.item.enums.FilterMode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.util.Pair;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface ItemService {
    Item createItemWithCategoryAndImages(String username, Item item, List<String> categories, MultipartFile[] images);
    Item getItem(UUID itemId);

    SearchQueryOutputDto getItemsSearchPageableSortingFiltering(List<String> categories, String searchTerm, FilterMode active, String username, FilterMode isEnded, int pageNumber, int itemCount, String sortField, Sort.Direction sortDirection);
}
