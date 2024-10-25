package com.cwc.ExceptionHandling_Validation_Security.dto;

import com.cwc.ExceptionHandling_Validation_Security.custom.annotations.ValidAuthority;
import com.cwc.ExceptionHandling_Validation_Security.entity.Authority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequest {
    @NotBlank(message = "Username is required..")
    private String username;

    @NotBlank(message = "Password is required")
    private String password;

    @NotEmpty(message = "At least one authority must be provided")
    @ValidAuthority(message = "Invalid Authority")
    private List<String> authorities;
}
