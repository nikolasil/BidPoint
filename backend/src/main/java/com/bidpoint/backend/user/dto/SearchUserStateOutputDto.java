package com.bidpoint.backend.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class SearchUserStateOutputDto {
    private int pageNumber;
    private int itemCount;
    private String sortField;
    private String sortDirection;
    private String searchTerm;
    private String approved;
}
