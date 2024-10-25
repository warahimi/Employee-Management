package com.cwc.ExceptionHandling_Validation_Security.repository;

import com.cwc.ExceptionHandling_Validation_Security.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByFirstName(String firstName);
    void deleteByFirstName(String firstName);
}
