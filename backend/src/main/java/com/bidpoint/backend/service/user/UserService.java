package com.bidpoint.backend.service.user;

import com.bidpoint.backend.dto.user.UserInputDto;
import com.bidpoint.backend.dto.user.UserOutputDto;
import com.bidpoint.backend.entity.Role;
import com.bidpoint.backend.entity.User;

import java.util.List;

public interface UserService {
    UserOutputDto getUser(String username);
    List<UserOutputDto> getUsers();
    UserOutputDto createUser(UserInputDto user, List<String> roles);
    UserOutputDto approveUser(String username);
    Boolean isApproved(String username);
    UserOutputDto removeRoleFromUser(String username, String roleName);
    UserOutputDto addRoleToUser(String username, String roleName);
}
