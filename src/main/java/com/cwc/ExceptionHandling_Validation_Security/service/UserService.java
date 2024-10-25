package com.cwc.ExceptionHandling_Validation_Security.service;

import com.cwc.ExceptionHandling_Validation_Security.dto.UserRequest;
import com.cwc.ExceptionHandling_Validation_Security.dto.UserResponse;
import com.cwc.ExceptionHandling_Validation_Security.entity.Authority;
import com.cwc.ExceptionHandling_Validation_Security.entity.User;
import com.cwc.ExceptionHandling_Validation_Security.exception.UserNotCreatedException;
import com.cwc.ExceptionHandling_Validation_Security.exception.UserNotFoundException;
import com.cwc.ExceptionHandling_Validation_Security.repository.AuthorityRepository;
import com.cwc.ExceptionHandling_Validation_Security.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;

    @Transactional
    public boolean createUserWithAuthorities(UserRequest userRequest) throws UserNotCreatedException {
        try {
            // Encode the password before saving
            String encodedPassword = passwordEncoder.encode(userRequest.getPassword());

            // Create a new User entity
            User newUser = User.builder()
                    .username(userRequest.getUsername())
                    .password(encodedPassword)
                    .enabled(true)
                    .build();

            // Save the user to the database
            User savedUser = userRepository.save(newUser);

            // Create and save the associated authorities
            for (String authorityName : userRequest.getAuthorities()) {
                // Create Authority object from the string
                Authority newAuthority = Authority.builder()
                        .username(userRequest.getUsername())
                        .authority(authorityName)  // This is the role/authority string
                        .user(savedUser)  // Link it to the newly created user
                        .build();

                // Save the authority in the database
                authorityRepository.save(newAuthority);
            }


            return true;
        }catch (DataIntegrityViolationException e) {
            throw new UserNotCreatedException("User already exists with username: " + userRequest.getUsername());
        } catch (Exception e) {
            throw new UserNotCreatedException("Failed to create User with username: " + userRequest.getUsername() + " " + e.getMessage());
        }
    }

    public List<UserResponse> getAllUsers() throws UserNotFoundException {
        List<User> users = userRepository.findAll();
        if(users.isEmpty())
        {
            throw new UserNotFoundException("There is no users in databse");
        }

        return users.stream()
                .map(this::mapUserToUserResponse)
                .collect(Collectors.toList());
    }
    private UserResponse mapUserToUserResponse(User user)
    {
        if(user == null)return null;
        List<String> authorities = new ArrayList<>();
        for(Authority authority : user.getAuthorities())
        {
            authorities.add(authority.getAuthority());
        }
        return UserResponse.builder()
                .id(user.getId())
                .userName(user.getUsername())
                .authorities(authorities)
                .enabled(user.isEnabled())
                .build();
    }
    public UserResponse finByUsername(String username)
    {
        User user = userRepository.findByUsername(username);
        return mapUserToUserResponse(user);
    }
}
