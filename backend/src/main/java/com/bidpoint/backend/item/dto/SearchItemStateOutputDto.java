package com.bidpoint.backend.item.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class SearchItemStateOutputDto {
    private int pageNumber;
    private int itemCount;
    private String sortField;
    private String sortDirection;
    private String searchTerm;
    private String active;
    private String isEnded;
    private String username;
    private List<String> categories;
}
