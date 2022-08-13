package com.bidpoint.backend.user.service;

import com.bidpoint.backend.user.entity.User;

import java.util.List;

public interface UserService {
    User getUser(String username);
    List<User> getUsers();
    User createUser(User user, List<String> roles);
    User approveUser(String username);
    Boolean isApproved(String username);
    User removeRoleFromUser(String username, String roleName);
    User addRoleToUser(String username, String roleName);
}
