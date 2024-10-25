package com.cwc.ExceptionHandling_Validation_Security.service;

import com.cwc.ExceptionHandling_Validation_Security.dto.EmployeeRequest;
import com.cwc.ExceptionHandling_Validation_Security.dto.EmployeeResponse;
import com.cwc.ExceptionHandling_Validation_Security.entity.Employee;
import com.cwc.ExceptionHandling_Validation_Security.exception.EmployeesNotFoundException;
import com.cwc.ExceptionHandling_Validation_Security.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // This allows for initializing the mocks and injecting them where necessary.
class EmployeeServiceTest {

    /*
    @Mock and @InjectMocks:
    @Mock is used to mock the EmployeeRepository object, while
    @InjectMocks injects the mock repository into the EmployeeService service class.
     */
    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    private Employee employee;
    private EmployeeRequest employeeRequest;
    private EmployeeResponse employeeResponse;

    @BeforeEach // Sets up test data before each test is executed.
    void setup() {
        // Initialize test data with LocalDate for dateOfBirth
        LocalDate dateOfBirth = LocalDate.parse("1990-01-01");
        // Initialize test data
        employee = new Employee(1L, "John", "Doe", dateOfBirth, "john@example.com", "1234567890", "Male", 30, "American", List.of("Pizza"), true);
        employeeRequest = new EmployeeRequest("John", "Doe", dateOfBirth, "john@example.com", "1234567890", "Male", 30, "American", List.of("Pizza"), true);
        employeeResponse = new EmployeeResponse(1L, "John", "Doe",dateOfBirth, "john@example.com", "1234567890", "Male", 30, "American", List.of("Pizza"), true);
    }

    @Test
    void getAllEmployees_whenEmployeesExist_shouldReturnEmployeeList() throws EmployeesNotFoundException {
        // Mock the repository's findAll method to return a list with one employee
        when(employeeRepository.findAll()).thenReturn(List.of(employee));

        // Call the service method
        List<EmployeeResponse> employees = employeeService.getAllEmployees();

        // Verify the repository method was called
        verify(employeeRepository, times(1)).findAll();

        // Assert the result
        assertNotNull(employees);
        assertEquals(1, employees.size());
        assertEquals("John", employees.get(0).getFirstName());
    }

    @Test
    void getAllEmployees_whenNoEmployeesExist_shouldThrowException() {
        // Mock the repository's findAll method to return an empty list
        when(employeeRepository.findAll()).thenReturn(Collections.emptyList());

        // Call the service method and expect an exception
        EmployeesNotFoundException exception = assertThrows(EmployeesNotFoundException.class, () -> employeeService.getAllEmployees());

        // Verify the repository method was called
        verify(employeeRepository, times(1)).findAll();

        // Assert the exception message
        assertEquals("There is no employee in the database. Please first insert some employees", exception.getMessage());
    }

    @Test
    void addEmployee_shouldReturnSavedEmployeeResponse() {
        // Mock the repository's save method to return a saved employee
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        // Call the service method
        EmployeeResponse response = employeeService.addEmployee(employeeRequest);

        // Verify the repository method was called
        verify(employeeRepository, times(1)).save(any(Employee.class));

        // Assert the result
        assertNotNull(response);
        assertEquals("John", response.getFirstName());
    }

    @Test
    void getEmployeeById_whenEmployeeExists_shouldReturnEmployeeResponse() throws EmployeesNotFoundException {
        // Mock the repository's findById method to return an Optional of employee
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        // Call the service method
        EmployeeResponse response = employeeService.getEmployeeById(1L);

        // Verify the repository method was called
        verify(employeeRepository, times(1)).findById(1L);

        // Assert the result
        assertNotNull(response);
        assertEquals("John", response.getFirstName());
    }

    @Test
    void getEmployeeById_whenEmployeeDoesNotExist_shouldThrowException() {
        // Mock the repository's findById method to return an empty Optional
        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

        // Call the service method and expect an exception
        EmployeesNotFoundException exception = assertThrows(EmployeesNotFoundException.class, () -> employeeService.getEmployeeById(1L));

        // Verify the repository method was called
        verify(employeeRepository, times(1)).findById(1L);

        // Assert the exception message
        assertEquals("Employee with id 1 not found in Databse", exception.getMessage());
    }

    @Test
    void deleteEmployeeById_whenEmployeeExists_shouldReturnDeletedEmployeeResponse() throws EmployeesNotFoundException {
        // Mock the repository's findById method to return an Optional of employee
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        // Call the service method
        EmployeeResponse response = employeeService.deleteEmployeeById(1L);

        // Verify the repository's deleteById method was called
        verify(employeeRepository, times(1)).deleteById(1L);

        // Assert the result
        assertNotNull(response);
        assertEquals("John", response.getFirstName());
    }

    @Test
    void updateEmployee_whenEmployeeExists_shouldReturnUpdatedEmployeeResponse() throws EmployeesNotFoundException {
        // Mock the repository's findById and save methods
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        // Call the service method
        EmployeeResponse response = employeeService.updateEmployee(1L, employeeRequest);

        // Verify the repository methods were called
        verify(employeeRepository, times(1)).findById(1L);
        verify(employeeRepository, times(1)).save(any(Employee.class));

        // Assert the result
        assertNotNull(response);
        assertEquals("John", response.getFirstName());
    }

    @Test
    void getEmployeeByFirstName_whenEmployeeExists_shouldReturnEmployeeResponse() throws EmployeesNotFoundException {
        // Mock the repository's findByFirstName method to return an Optional of employee
        when(employeeRepository.findByFirstName("John")).thenReturn(Optional.of(employee));

        // Call the service method
        EmployeeResponse response = employeeService.getEmployeeByFirstName("John");

        // Verify the repository method was called
        verify(employeeRepository, times(1)).findByFirstName("John");

        // Assert the result
        assertNotNull(response);
        assertEquals("John", response.getFirstName());
    }

    @Test
    void deleteEmployeeByFirstName_whenEmployeeExists_shouldReturnDeletedEmployeeResponse() throws EmployeesNotFoundException {
        // Mock the repository's findByFirstName method to return an Optional of employee
        when(employeeRepository.findByFirstName("John")).thenReturn(Optional.of(employee));

        // Call the service method
        EmployeeResponse response = employeeService.deleteEmployeeByFirstName("John");

        // Verify the repository's deleteByFirstName method was called
        verify(employeeRepository, times(1)).deleteByFirstName("John");

        // Assert the result
        assertNotNull(response);
        assertEquals("John", response.getFirstName());
    }
}