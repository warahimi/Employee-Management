package com.cwc.ExceptionHandling_Validation_Security.service;

import com.cwc.ExceptionHandling_Validation_Security.entity.User;
import com.cwc.ExceptionHandling_Validation_Security.model.UserPrincipal;
import com.cwc.ExceptionHandling_Validation_Security.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    @Transactional // Ensures a Hibernate session is available
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        System.out.println("inside loadUserByUsername");
        System.out.println("Username: "+ username);
        System.out.println("User" + user);
        if (user == null) {
            throw new UsernameNotFoundException("Username: " + username + " not found in database");
        }
        // Access the authorities to initialize the collection within the transaction
        user.getAuthorities().size(); // This ensures the collection is loaded
        return new UserPrincipal(user);
    }
}
