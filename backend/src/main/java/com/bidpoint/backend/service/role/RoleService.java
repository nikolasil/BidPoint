package com.bidpoint.backend.service.role;

import com.bidpoint.backend.entity.Role;
import com.bidpoint.backend.entity.User;

import java.util.List;

public interface RoleService {
    Role getRole(String roleName);
    List<Role> getRoles();
    Role createRole(Role role);
}
