package com.cwc.ExceptionHandling_Validation_Security.service;

import com.cwc.ExceptionHandling_Validation_Security.dto.EmployeeRequest;
import com.cwc.ExceptionHandling_Validation_Security.dto.EmployeeResponse;
import com.cwc.ExceptionHandling_Validation_Security.entity.Employee;
import com.cwc.ExceptionHandling_Validation_Security.exception.EmployeesNotFoundException;
import com.cwc.ExceptionHandling_Validation_Security.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);


    public List<EmployeeResponse> getAllEmployees() throws EmployeesNotFoundException {
        List<Employee> allEmployees = employeeRepository.findAll();
        if(allEmployees.isEmpty())
        {
            logger.warn("No employees found in the database");
            throw new EmployeesNotFoundException("There is no employee in the database. Please first insert some employees");
        }
        logger.info("Returning {} employees", allEmployees.size());
        return allEmployees.stream()
                .map(this::mapEmployeeToEmployeeResponse)
                .collect(Collectors.toList());
    }
    public EmployeeResponse addEmployee(EmployeeRequest employeeRequest)
    {
        Employee savedEmployee = employeeRepository.save(mapEmployeeRequestToEmployee(employeeRequest));
        return mapEmployeeToEmployeeResponse(savedEmployee);
    }
    private Employee mapEmployeeRequestToEmployee(EmployeeRequest employeeRequest)
    {
        return Employee.builder()
                .id(0L)
                .firstName(employeeRequest.getFirstName())
                .lastName(employeeRequest.getLastName())
                .dateOfBirth(employeeRequest.getDateOfBirth())
                .email(employeeRequest.getEmail())
                .phone(employeeRequest.getPhone())
                .gender(employeeRequest.getGender())
                .age(employeeRequest.getAge())
                .nationality(employeeRequest.getNationality())
                .favoriteFoods(employeeRequest.getFavoriteFoods())
                .isHealthy(employeeRequest.getIsHealthy())
                .build();
    }
    private EmployeeResponse mapEmployeeToEmployeeResponse(Employee employee)
    {
        return EmployeeResponse.builder()
                .id(employee.getId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .dateOfBirth(employee.getDateOfBirth())
                .email(employee.getEmail())
                .phone(employee.getPhone())
                .gender(employee.getGender())
                .age(employee.getAge())
                .nationality(employee.getNationality())
                .favoriteFoods(employee.getFavoriteFoods())
                .isHealthy(employee.getIsHealthy())
                .build();
    }


    public EmployeeResponse getEmployeeById(Long id) throws EmployeesNotFoundException {
        Optional<Employee> employee = employeeRepository.findById(id);
        if(!employee.isPresent())
        {
            throw new EmployeesNotFoundException("Employee with id " + id + " not found in Databse");
        }
        return mapEmployeeToEmployeeResponse(employee.get());
    }

    public EmployeeResponse deleteEmployeeById(long id) throws EmployeesNotFoundException {
        Optional<Employee> employee = employeeRepository.findById(id);
        if(!employee.isPresent())
        {
            throw new EmployeesNotFoundException("Employee with id " + id + " not found in database");
        }
        employeeRepository.deleteById(id);
        return mapEmployeeToEmployeeResponse(employee.get());
    }

    public EmployeeResponse updateEmployee(long id, EmployeeRequest employeeRequest) throws EmployeesNotFoundException {
        Optional<Employee> existingEmployee = Optional.ofNullable(employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeesNotFoundException("Employee with id " + id + " not found")));

        existingEmployee.get().setFirstName(employeeRequest.getFirstName());
        existingEmployee.get().setLastName(employeeRequest.getLastName());
        existingEmployee.get().setDateOfBirth(employeeRequest.getDateOfBirth());
        existingEmployee.get().setEmail(employeeRequest.getEmail());
        existingEmployee.get().setPhone(employeeRequest.getPhone());
        existingEmployee.get().setPhone(employeeRequest.getPhone());
        existingEmployee.get().setGender(employeeRequest.getGender());
        existingEmployee.get().setAge(employeeRequest.getAge());
        existingEmployee.get().setNationality(employeeRequest.getNationality());
        existingEmployee.get().setFavoriteFoods(employeeRequest.getFavoriteFoods());
        existingEmployee.get().setIsHealthy(employeeRequest.getIsHealthy());

        Employee savedEmployee = employeeRepository.save(existingEmployee.get());
        return mapEmployeeToEmployeeResponse(savedEmployee);
    }

    public EmployeeResponse getEmployeeByFirstName(String firstName) throws EmployeesNotFoundException {
        if (!firstName.matches("^[a-zA-Z]+$")) { // validate the First Name : should contain only character and no white space
            throw new IllegalArgumentException("First name must contain only characters and no spaces.");
        }

        Optional<Employee> byFirstName = Optional.ofNullable(employeeRepository.findByFirstName(firstName)
                .orElseThrow(() -> new EmployeesNotFoundException("Employee with first name: " + firstName + " not found")));
        return mapEmployeeToEmployeeResponse(byFirstName.get());
    }
    @Transactional
    public EmployeeResponse deleteEmployeeByFirstName(String firstName) throws EmployeesNotFoundException {
        if (!firstName.matches("^[a-zA-Z]+$")) { // validate the First Name : should contain only character and no white space
            throw new IllegalArgumentException("First name must contain only characters and no spaces.");
        }

        Optional<Employee> byFirstName = Optional.ofNullable(employeeRepository.findByFirstName(firstName)
                .orElseThrow(() -> new EmployeesNotFoundException("Employee with first name: " + firstName + " not found")));

        employeeRepository.deleteByFirstName(firstName);
        return mapEmployeeToEmployeeResponse(byFirstName.get());
    }
}
