package com.bidpoint.backend.item.repository;

import com.bidpoint.backend.item.dto.SearchItemQueryOutputDto;
import com.bidpoint.backend.enums.FilterMode;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface ItemRepositoryCustom {
    SearchItemQueryOutputDto getItemsSearchPageableSortingFiltering(List<String> categories, String searchTerm, FilterMode active, String username, FilterMode isEnded, PageRequest pageable);
}