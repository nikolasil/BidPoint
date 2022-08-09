package com.bidpoint.backend.service.role;

import com.bidpoint.backend.entity.Role;
import com.bidpoint.backend.entity.User;
import com.bidpoint.backend.exception.role.RoleAlreadyExistsException;
import com.bidpoint.backend.exception.role.RoleNotFoundException;
import com.bidpoint.backend.exception.user.UserNotFoundException;
import com.bidpoint.backend.repository.RoleRepository;
import com.bidpoint.backend.repository.UserRepository;
import com.bidpoint.backend.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
