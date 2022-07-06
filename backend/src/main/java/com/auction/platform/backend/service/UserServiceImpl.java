package com.auction.platform.backend.service;

import com.auction.platform.backend.entity.Role;
import com.auction.platform.backend.entity.User;
import com.auction.platform.backend.repository.RoleRepository;
import com.auction.platform.backend.repository.UserRepository;
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

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user == null){
            log.error("User with username {} not found in the database", username);
            throw new UsernameNotFoundException("User not found in the database");
        } else{
            log.info("User found in the database: {}",username);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),authorities);
    }

    @Override
    public User saveUser(User user) {
        log.info("Saving new user {} to the database",user.getName());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("Saving new role {} to the database",role.getName());
        Role foundRole = roleRepository.findByName(role.getName());
        if(foundRole == null)
            return roleRepository.save(role);
        return foundRole;
    }

    @Override
    public Role getRole(String roleName) {
        log.info("Getting role {} from the database", roleName);
        return roleRepository.findByName(roleName);
    }

    @Override
    public User addRoleToUser(String username, String roleName) {
        log.info("Adding role {} to username {}", roleName, username);
        User user = userRepository.findByUsername(username);

        if(user == null)
            throw new RuntimeException("Username " + username + " not found");

        Role role = roleRepository.findByName(roleName);
        if(role == null)
            throw new RuntimeException("roleName " + roleName + " not found");

        Collection<Role> roles = user.getRoles();

        if(!roles.contains(role))
            roles.add(role);

        return user;
    }

    @Override
    public User removeRoleFromUser(String username, String roleName) {
        log.info("Removing role {} from username {}", roleName, username);
        User user = userRepository.findByUsername(username);

        if(user == null)
            throw new RuntimeException("Username " + username + " not found");

        Role role = roleRepository.findByName(roleName);
        if(role == null)
            throw new RuntimeException("roleName " + roleName + " not found");

        user.getRoles().remove(role);
        return user;
    }

    @Override
    public User getUser(String username) {
        log.info("Fetching user with username {}", username);
        return userRepository.findByUsername(username);
    }

    @Override
    public List<User> getUsers() {
        log.info("Fetching all users");
        return userRepository.findAll();
    }

}
