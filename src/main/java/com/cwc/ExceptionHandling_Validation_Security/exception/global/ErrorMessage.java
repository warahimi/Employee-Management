package com.cwc.ExceptionHandling_Validation_Security.exception.global;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorMessage {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy") // Format the date to "MM/dd/yyyy"

    private LocalDate date;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "hh:mm a") // Format the time to "hh:mm a"

    private LocalTime time;
    private String status;
    private String error;
    private String message;
}
