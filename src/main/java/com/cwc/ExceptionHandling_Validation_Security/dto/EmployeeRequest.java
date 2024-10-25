package com.cwc.ExceptionHandling_Validation_Security.dto;


import com.cwc.ExceptionHandling_Validation_Security.custom.annotations.DateFormat;
import com.cwc.ExceptionHandling_Validation_Security.custom.annotations.MinAge;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Component
@Builder
public class EmployeeRequest {
    @NotBlank(message = "First Name cannot be blank")
    @Pattern(regexp = "^[A-Za-z]+$", message = "First Name should contain only letters")
    @Size(min = 3, max = 50, message = "First Name must be between 3 to 50 characters")

    private String firstName;
    @NotBlank(message = "Last Name cannot be blank")
    @Pattern(regexp = "^[A-Za-z]+$", message = "Last Name should contain only letters")
    @Size(min = 3, max = 50, message = "Last Name must be between 3 to 50 characters")
    private String lastName;

    @Past(message = "Date of birt must be in the past")
    @MinAge(value = 18, message = "Age must be at lease 18 years old. Please enter a valid DoB") // Custom annotation to ensure minimum age
    @NotNull(message = "Date of birth cannot be null")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy") // Format the date to "MM/dd/yyyy"
    @DateFormat(pattern = "MM/dd/yyyy", message = "Invalid date format. Please use MM/dd/yyyy")
    private LocalDate dateOfBirth;

    @Email(message = "Invalid Email, Please Enter a valid email address")
    @NotBlank(message = "Email is required")
    private String email;

    @Pattern(regexp = "^\\d{10}$", message = "Phone number must be 10 digits")
    private String phone;

    @Pattern(regexp = "^(Male|Female|M|F)$", message = "Gender must be Male, Female, M, or F")
    @NotBlank(message = "Gender is required")
    private String gender;

    @Min(value = 18, message = "Age must be greater than 18 and less than 60")
    @Max(value = 60, message = "Age must be between less than 60 and greater than 18")
    private int age;

    @NotEmpty(message = "Nationality cannot be empty")
    private String nationality;

    @NotEmpty(message = "List of favorite foods cannot be empty")
    @Size(min = 1, max = 10, message = "You can specify up to 10 favorite foods")
    private List<String> favoriteFoods;

    // isHealthy must be a boolean (true or false)
    @NotNull(message = "isHealthy status is required")
    private Boolean isHealthy;

}
