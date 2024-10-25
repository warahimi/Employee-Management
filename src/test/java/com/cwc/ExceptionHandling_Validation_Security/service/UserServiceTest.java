package com.cwc.ExceptionHandling_Validation_Security.service;

import com.cwc.ExceptionHandling_Validation_Security.dto.UserRequest;
import com.cwc.ExceptionHandling_Validation_Security.dto.UserResponse;
import com.cwc.ExceptionHandling_Validation_Security.entity.Authority;
import com.cwc.ExceptionHandling_Validation_Security.entity.User;
import com.cwc.ExceptionHandling_Validation_Security.exception.UserNotCreatedException;
import com.cwc.ExceptionHandling_Validation_Security.exception.UserNotFoundException;
import com.cwc.ExceptionHandling_Validation_Security.repository.UserRepository;
import com.cwc.ExceptionHandling_Validation_Security.repository.AuthorityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthorityRepository authorityRepository;

    @InjectMocks
    private UserService userService;

    private UserRequest userRequest;
    private User user;
    private Authority authority;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Test data setup
        userRequest = new UserRequest("testUser", "password123", List.of("ROLE_USER"));
        user = User.builder()
                .username("testUser")
                .password("encodedPassword")
                .enabled(true)
                .build();
        authority = Authority.builder()
                .username("testUser")
                .authority("ROLE_USER")
                .user(user)
                .build();
    }

    /*
    This test checks if a user and their authorities are created successfully.
    It mocks the password encoding and the save operations, then verifies that the methods were called once.
     */
    @Test
    void createUserWithAuthorities_shouldCreateUserAndAuthoritiesSuccessfully() throws UserNotCreatedException {
        // Arrange
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        // Act
        boolean result = userService.createUserWithAuthorities(userRequest);

        // Assert
        assertTrue(result);
        verify(passwordEncoder, times(1)).encode(userRequest.getPassword());
        verify(userRepository, times(1)).save(any(User.class));
        verify(authorityRepository, times(1)).save(any(Authority.class));
    }

    /*
    This test verifies that an exception is thrown when trying to create a user that already exists.
    It simulates a DataIntegrityViolationException and checks that the exception is correctly thrown.
     */
    @Test
    void createUserWithAuthorities_shouldThrowExceptionIfUserAlreadyExists() {
        // Arrange
        when(userRepository.save(any(User.class))).thenThrow(new DataIntegrityViolationException("User already exists"));

        // Act & Assert
        assertThrows(UserNotCreatedException.class, () -> userService.createUserWithAuthorities(userRequest));
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void getAllUsers_shouldReturnListOfUsers() throws UserNotFoundException {
        // Arrange
        List<User> userList = List.of(user);
        when(userRepository.findAll()).thenReturn(userList);

        // Act
        List<UserResponse> result = userService.getAllUsers();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("testUser", result.get(0).getUserName());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void getAllUsers_shouldThrowExceptionWhenNoUsersFound() {
        // Arrange
        when(userRepository.findAll()).thenReturn(List.of());

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> userService.getAllUsers());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void finByUsername_shouldReturnUserResponse() {
        // Arrange
        when(userRepository.findByUsername("testUser")).thenReturn(user);
        when(user.getAuthorities()).thenReturn(List.of(authority));

        // Act
        UserResponse result = userService.finByUsername("testUser");

        // Assert
        assertNotNull(result);
        assertEquals("testUser", result.getUserName());
        assertEquals(1, result.getAuthorities().size());
        assertEquals("ROLE_USER", result.getAuthorities().get(0));
        verify(userRepository, times(1)).findByUsername("testUser");
    }

    @Test
    void finByUsername_shouldReturnNullWhenUserNotFound() {
        // Arrange
        when(userRepository.findByUsername("unknownUser")).thenReturn(null);

        // Act
        UserResponse result = userService.finByUsername("unknownUser");

        // Assert
        assertNull(result);
        verify(userRepository, times(1)).findByUsername("unknownUser");
    }
}