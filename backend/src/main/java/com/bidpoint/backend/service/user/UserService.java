package com.bidpoint.backend.service.user;

import com.bidpoint.backend.entity.Role;
import com.bidpoint.backend.entity.User;

import java.util.List;

public interface UserService {
    User getUser(String username);
    List<User> getUsers();
    User createUser(User user, List<String> roles);
    User approveUser(String username);
    User removeRoleFromUser(String username, String roleName);
    User addRoleToUser(String username, String roleName);
}
