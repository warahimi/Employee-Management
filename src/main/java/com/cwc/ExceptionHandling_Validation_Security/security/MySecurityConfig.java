package com.cwc.ExceptionHandling_Validation_Security.security;

import com.cwc.ExceptionHandling_Validation_Security.service.MyUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // to tell spring do not go with default security filter chain, flow my configuration
@RequiredArgsConstructor
public class MySecurityConfig {
    private final MyUserDetailsService userDetailsService;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(csrf -> csrf.disable()) // Disable CSRF protection for API use cases
                .authorizeHttpRequests(authorize -> authorize
                        // Permit access to "/getUserByName" without authentication
                        .requestMatchers("/api/user/getUserByName/*").permitAll()
                        .requestMatchers("/api/user/register").permitAll()
                        // All other requests require authentication
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults()) // Basic Authentication for Postman and API clients
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)); // Stateless session

        return httpSecurity.build();
    }

//    @Bean
//    public UserDetailsService userDetailsService()
//    {
//        UserDetails user1  =
//                User.builder()
//                        .username("hosna")
//                        .password(passwordEncoder().encode("hosna"))
//                        .roles("USER")
//                        .build();
//        UserDetails user2  =
//                User.builder()
//                        .username("wahid")
//                        .password(passwordEncoder().encode("wahid"))
//                        .roles("ADMIN")
//                        .build();
//        return new InMemoryUserDetailsManager(user1);
//    }



    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());  // BCryptPasswordEncoder
        provider.setUserDetailsService(userDetailsService);  // MyUserDetailsService
        return provider;
    }
    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }
}
