package com.bidpoint.backend.user.dto;

import com.bidpoint.backend.item.dto.ItemOutputDto;
import com.bidpoint.backend.item.dto.SearchItemStateOutputDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PageUsersOutputDto {
    private Long totalUsers;
    private List<UserOutputDto> users;
    private SearchUserStateOutputDto searchState;
}
