package com.bidpoint.backend.user.service;

import com.bidpoint.backend.auth.exception.UserNotApprovedException;
import com.bidpoint.backend.enums.FilterMode;
import com.bidpoint.backend.role.entity.Role;
import com.bidpoint.backend.role.exception.RoleNotFoundException;
import com.bidpoint.backend.role.repository.RoleRepository;
import com.bidpoint.backend.user.dto.SearchUserQueryOutputDto;
import com.bidpoint.backend.user.entity.User;
import com.bidpoint.backend.user.exception.UserAlreadyExistsException;
import com.bidpoint.backend.user.exception.UserNotFoundException;
import com.bidpoint.backend.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UserNotFoundException, UserNotApprovedException {
        log.info("loadUserByUsername username={}",username);

        User user = userRepository.findByUsername(username);

        if(user == null){
            log.info("User with username {} not found in the database", username);
            throw new UserNotFoundException(username);
        }

//        if(!user.isApproved())
//            throw new UserNotApprovedException(username);

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),authorities);
    }

    @Override
    public User createUser(User user, List<String> roles) {

        log.info("createUser user={} roles={}", user.getUsername(), roles);

        if(userRepository.findByUsername(user.getUsername()) != null)
            throw new UserAlreadyExistsException(user.getUsername());

        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        map the List<String> to Set<Role>. The role names that are not present in the database are ignored
        if(roles.contains("admin"))
            user.setApproved(true);

        roles.stream().map(roleRepository::findByName).forEach(user::addRole);

        return userRepository.save(user);
    }

    @Override
    public User approveUser(String username) {
        log.info("approveUser username={}",username);

        User user = userRepository.findByUsername(username);
        if(user == null)
            throw new UserNotFoundException(username);

        user.setApproved(true);
        return user;
    }

    @Override
    public Boolean isApproved(String username) {
        log.info("isApproved username={}", username);

        User user = userRepository.findByUsername(username);
        if(user == null)
            throw new UserNotFoundException(username);

        return user.isApproved();
    }

    @Override
    public User addRoleToUser(String username, String roleName) {
        log.info("addRoleToUser username={} roleName={}", username, roleName);

        User user = userRepository.findByUsername(username);
        if(user == null)
            throw new UserNotFoundException(username);

        Role role = roleRepository.findByName(roleName);
        if(role == null)
            throw new RoleNotFoundException(roleName);

        user.addRole(role);

        return user;
    }

    @Override
    public List<User> getAllOrdered() {
        return userRepository.findAllByOrderByIdAsc();
    }

    @Override
    public User removeRoleFromUser(String username, String roleName) {
        log.info("removeRoleFromUser username={} roleName={}", username, roleName);

        User user = userRepository.findByUsername(username);
        if(user == null)
            throw new UserNotFoundException("Username " + username + " not found");

        Role role = roleRepository.findByName(roleName);
        if(role == null)
            throw new RoleNotFoundException("roleName " + roleName + " not found");

        user.removeRole(role);

        return user;
    }

    @Override
    public User getUser(String username) {
        log.info("getUser username={}", username);

        User user = userRepository.findByUsername(username);
        if(user == null)
            throw new UserNotFoundException(username);
        return user;
    }

    @Override
    public List<User> getUsers() {
        log.info("getUsers");

        return userRepository.findAll();
    }

    @Override
    public SearchUserQueryOutputDto getUsersSearchPageableSorting(String searchTerm, FilterMode approved, int pageNumber, int itemCount, String sortField, Sort.Direction sortDirection) {
        return userRepository.getUsersSearchPageableSorting(searchTerm, approved, PageRequest.of(pageNumber, itemCount).withSort(Sort.by(sortDirection, sortField)));
    }

    @Override
    public List<User> getUserByApproved(boolean value) {
        log.info("getNotApprovedUsers");

        return userRepository.findByApproved(value);
    }

}
