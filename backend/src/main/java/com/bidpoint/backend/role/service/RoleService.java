package com.bidpoint.backend.role.service;

import com.bidpoint.backend.user.entity.Role;

import java.util.List;

public interface RoleService {
    Role getRole(String roleName);
    List<Role> getRoles();
    Role createRole(Role role);
}
