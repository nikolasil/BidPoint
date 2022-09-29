package com.bidpoint.backend.user.service;

import com.bidpoint.backend.user.dto.SearchUserQueryOutputDto;
import com.bidpoint.backend.enums.FilterMode;
import com.bidpoint.backend.user.entity.User;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface UserService {
    User getUser(String username);
    List<User> getUsers();
    SearchUserQueryOutputDto getUsersSearchPageableSorting(String searchTerm, FilterMode approved, int pageNumber, int itemCount, String sortField, Sort.Direction sortDirection);
    List<User> getUserByApproved(boolean value);
    User createUser(User user, List<String> roles);
    User approveUser(String username);
    Boolean isApproved(String username);
    User removeRoleFromUser(String username, String roleName);
    User addRoleToUser(String username, String roleName);

    List<User> getAllOrdered();
}
