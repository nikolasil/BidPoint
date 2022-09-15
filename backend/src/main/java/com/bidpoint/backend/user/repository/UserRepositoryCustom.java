package com.bidpoint.backend.user.repository;

import com.bidpoint.backend.user.dto.SearchUserQueryOutputDto;
import com.bidpoint.backend.enums.FilterMode;
import org.springframework.data.domain.PageRequest;

public interface UserRepositoryCustom {
    SearchUserQueryOutputDto getUsersSearchPageableSorting(String searchTerm, FilterMode approved, PageRequest pageable);
}