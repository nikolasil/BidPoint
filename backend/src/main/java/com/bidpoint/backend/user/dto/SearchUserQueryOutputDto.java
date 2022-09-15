package com.bidpoint.backend.user.dto;

import com.bidpoint.backend.item.entity.Item;
import com.bidpoint.backend.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Page;

@Data
@AllArgsConstructor
public class SearchUserQueryOutputDto {
    private Page<User> users;
    private Long totalItems;
}
