package com.bidpoint.backend.user.repository;

import com.bidpoint.backend.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {
    User findByUsername(String username);
    List<User> findByApproved(boolean approved);
}
