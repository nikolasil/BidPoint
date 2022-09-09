package com.bidpoint.backend.item.repository;

import com.bidpoint.backend.item.entity.Category;
import com.bidpoint.backend.item.entity.Item;
import com.bidpoint.backend.item.enums.FilterMode;
import com.bidpoint.backend.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.ZonedDateTime;
import java.util.List;

public interface ItemRepositoryCustom {
    Page<Item> getItemsSearchPageableSortingFiltering(List<String> categories, String searchTerm, FilterMode active, String username, FilterMode isEnded, Pageable pageable);
}