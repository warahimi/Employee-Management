package com.cwc.ExceptionHandling_Validation_Security.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "t_employee")
@Builder
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
