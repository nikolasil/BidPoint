package com.bidpoint.backend.service;

import com.bidpoint.backend.entity.Role;
import com.bidpoint.backend.entity.User;

import java.util.List;

public interface UserService {
    User saveUser(User user);
    Role saveRole(Role role);
    Role getRole(String roleName);
    User addRoleToUser(String username,String roleName);
    User removeRoleFromUser(String username,String roleName);
    User getUser(String username);
    List<User> getUsers();
}
