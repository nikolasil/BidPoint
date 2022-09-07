package com.bidpoint.backend.item.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class SearchStateOutputDto {
    private int pageNumber;
    private int itemCount;
    private String sortField;
    private String sortDirection;
    private String searchTerm;
}
