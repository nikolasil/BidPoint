package com.bidpoint.backend.user.repository;

import com.bidpoint.backend.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID>, UserRepositoryCustom {
    User findByUsername(String username);
    List<User> findByApproved(boolean approved);
}
