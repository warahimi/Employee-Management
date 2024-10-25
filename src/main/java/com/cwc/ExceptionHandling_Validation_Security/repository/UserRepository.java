package com.cwc.ExceptionHandling_Validation_Security.repository;

import com.cwc.ExceptionHandling_Validation_Security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}