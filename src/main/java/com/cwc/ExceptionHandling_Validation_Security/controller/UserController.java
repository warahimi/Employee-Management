package com.cwc.ExceptionHandling_Validation_Security.controller;

import com.cwc.ExceptionHandling_Validation_Security.dto.UserRequest;
import com.cwc.ExceptionHandling_Validation_Security.dto.UserResponse;
import com.cwc.ExceptionHandling_Validation_Security.entity.User;
import com.cwc.ExceptionHandling_Validation_Security.exception.UserNotCreatedException;
import com.cwc.ExceptionHandling_Validation_Security.exception.UserNotFoundException;
import com.cwc.ExceptionHandling_Validation_Security.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;


    // REST API to register a new user
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserRequest userRequest) throws UserNotCreatedException {
        boolean registered = userService.createUserWithAuthorities(userRequest);
        if (registered) {
            return new ResponseEntity<>("User successfully created", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("User not successfully created", HttpStatus.SERVICE_UNAVAILABLE);
        }
    }
    @GetMapping()
    public ResponseEntity<List<UserResponse>> getAllUsers() throws UserNotFoundException {
        List<UserResponse> allUsers = userService.getAllUsers();
        return new ResponseEntity<>(allUsers, HttpStatus.FOUND);
    }

    @GetMapping("/session-id")
    public String getSessionId(HttpServletRequest request)
    {
        return request.getSession().getId();
    }
    @GetMapping("/csrf-token")
    public CsrfToken getCsrfToken(HttpServletRequest request)
    {
        return (CsrfToken) request.getAttribute("_csrf");
    }

    @GetMapping("/getUserByName/{name}")
    public ResponseEntity<UserResponse> getUserByName(@PathVariable String name) throws UserNotFoundException {
        UserResponse user = userService.finByUsername(name);
        if(user == null)
        {
            throw new UserNotFoundException("User with username: "+ name + " not found.");
        }
        return ResponseEntity.ok(user);
    }
}