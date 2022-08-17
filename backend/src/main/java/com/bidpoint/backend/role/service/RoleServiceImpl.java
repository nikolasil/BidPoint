package com.bidpoint.backend.role.service;

import com.bidpoint.backend.role.entity.Role;
import com.bidpoint.backend.role.exception.RoleAlreadyExistsException;
import com.bidpoint.backend.role.exception.RoleNotFoundException;
import com.bidpoint.backend.role.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public Role createRole(Role role) {
        log.info("createRole role={}",role.getName());

        if(roleRepository.findByName(role.getName()) != null)
            throw new RoleAlreadyExistsException(role.getName());

        return roleRepository.save(role);
    }

    @Override
    public Role getRole(String roleName) {
        log.info("getRole roleName={}", roleName);

        Role role = roleRepository.findByName(roleName);
        if(role == null)
            throw new RoleNotFoundException(roleName);
        return role;
    }

    @Override
    public List<Role> getRoles() {
        log.info("getRoles");

        return roleRepository.findAll();
    }
}
