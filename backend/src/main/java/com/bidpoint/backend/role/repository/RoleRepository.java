package com.bidpoint.backend.role.repository;

import com.bidpoint.backend.role.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Role findByName(String name);
}

