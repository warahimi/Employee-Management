package com.cwc.ExceptionHandling_Validation_Security.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmployeeResponse {
    private Long id;
    private String firstName;
    private String lastName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy") // Format the date to "MM/dd/yyyy"
    private LocalDate dateOfBirth;
    private String email;
    private String phone;
    private String gender;
    private int age;
    private String nationality;
    private List<String> favoriteFoods;
    private Boolean isHealthy;
}
