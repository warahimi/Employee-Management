package com.cwc.ExceptionHandling_Validation_Security.repository;

import com.cwc.ExceptionHandling_Validation_Security.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    // Add any custom queries if needed
}