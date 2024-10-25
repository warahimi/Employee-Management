package com.cwc.ExceptionHandling_Validation_Security.startup;

import com.cwc.ExceptionHandling_Validation_Security.dto.EmployeeRequest;
import com.cwc.ExceptionHandling_Validation_Security.dto.UserRequest;
import com.cwc.ExceptionHandling_Validation_Security.entity.Employee;
import com.cwc.ExceptionHandling_Validation_Security.entity.User;
import com.cwc.ExceptionHandling_Validation_Security.repository.EmployeeRepository;
import com.cwc.ExceptionHandling_Validation_Security.repository.UserRepository;
import com.cwc.ExceptionHandling_Validation_Security.service.EmployeeService;
import com.cwc.ExceptionHandling_Validation_Security.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class DataInsert implements CommandLineRunner {
    private final EmployeeService employeeService;
    private final EmployeeRepository employeeRepository;

    private final UserRepository userRepository;
    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    public void run(String... args) throws Exception {
        if(employeeRepository.findAll().isEmpty()) // if no entry is in DB
        {
            EmployeeRequest emp = EmployeeRequest.builder()
                    .firstName("Wahidullah")
                    .lastName("Rahimi")
                    .dateOfBirth(LocalDate.now())
                    .email("wahidrahimi@gmail.com")
                    .phone("1234567891")
                    .gender("Male")
                    .age(34)
                    .nationality("Afghan")
                    .favoriteFoods(new ArrayList<>(Arrays.asList("Apple", "Banana")))
                    .isHealthy(true)
                    .build();
            employeeService.addEmployee(emp);
        }
        if(userRepository.findAll().isEmpty())
        {
            UserRequest userRequest = UserRequest.builder()
                    .username("wahid")
                    .password("admin")
                    .authorities(new ArrayList<>(Arrays.asList("USER", "ADMIN", "DEVELOPER", "MANAGER")))
                    .build();
            userService.createUserWithAuthorities(userRequest);

        }

    }
}
