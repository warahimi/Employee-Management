package com.cwc.ExceptionHandling_Validation_Security.controller;

import com.cwc.ExceptionHandling_Validation_Security.dto.EmployeeRequest;
import com.cwc.ExceptionHandling_Validation_Security.dto.EmployeeResponse;
import com.cwc.ExceptionHandling_Validation_Security.exception.EmployeesNotFoundException;
import com.cwc.ExceptionHandling_Validation_Security.service.EmployeeService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/emp")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;
    @GetMapping
    public ResponseEntity<List<EmployeeResponse>> getAllEmployee() throws EmployeesNotFoundException {
        List<EmployeeResponse> allEmployees = employeeService.getAllEmployees();
        return new ResponseEntity<>(allEmployees, HttpStatus.FOUND);
    }

    @PostMapping
    public ResponseEntity<EmployeeResponse> saveEmployee(@Valid @RequestBody EmployeeRequest employeeRequest)
    {
        EmployeeResponse employeeResponse = employeeService.addEmployee(employeeRequest);
        return new ResponseEntity<>(employeeResponse, HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getEmployeeById(@PathVariable long id) throws EmployeesNotFoundException {
        EmployeeResponse employeeById = employeeService.getEmployeeById(id);
        return new ResponseEntity<>(employeeById, HttpStatus.FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<EmployeeResponse> deleteEmployeeById(@PathVariable long id) throws EmployeesNotFoundException {
        EmployeeResponse employeeResponse = employeeService.deleteEmployeeById(id);
        return new ResponseEntity<>(employeeResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponse> updateEmployee(@PathVariable long id, @Valid @RequestBody EmployeeRequest employeeRequest) throws EmployeesNotFoundException {
        EmployeeResponse employeeResponse = employeeService.updateEmployee(id, employeeRequest);
        return new ResponseEntity<>(employeeResponse, HttpStatus.CREATED);
    }

    @GetMapping("/first-name/{firstName}")
    public ResponseEntity<EmployeeResponse> getEmployeeByFirstName(@PathVariable String firstName) throws EmployeesNotFoundException {
        EmployeeResponse employeeByFirstName = employeeService.getEmployeeByFirstName(firstName);
        return new ResponseEntity<>(employeeByFirstName, HttpStatus.FOUND);
    }
    @DeleteMapping("/first-name/{firstName}")
    public ResponseEntity<EmployeeResponse> deleteEmployeeByFirstName(@PathVariable
                                                                          //@Pattern(regexp = "^[a-zA-Z]+$", message = "First name must contain only characters and no spaces.")
                                                                          String firstName) throws EmployeesNotFoundException {
        EmployeeResponse employeeByFirstName = employeeService.deleteEmployeeByFirstName(firstName);
        return new ResponseEntity<>(employeeByFirstName, HttpStatus.FOUND);
    }




}
