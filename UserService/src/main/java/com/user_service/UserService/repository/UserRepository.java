package com.user_service.UserService.repository;

import com.user_service.UserService.model.RefreshToken;
import com.user_service.UserService.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);
}

