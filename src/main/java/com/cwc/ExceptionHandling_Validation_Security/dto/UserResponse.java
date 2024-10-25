package com.cwc.ExceptionHandling_Validation_Security.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserResponse {
    private long id;
    private String userName;
    private Boolean enabled;
    private List<String> authorities;
}
