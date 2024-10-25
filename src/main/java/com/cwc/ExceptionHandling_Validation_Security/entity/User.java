package com.cwc.ExceptionHandling_Validation_Security.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
/*
    @OneToMany(mappedBy = "user"): This establishes a one-to-many relationship between User and Authority.
    The mappedBy attribute indicates that the relationship is owned by the Authority entity, which has the
    user field.
    cascade = CascadeType.ALL: This ensures that when you save or delete a user,
    the associated authorities are saved or deleted as well.
    fetch = FetchType.LAZY: This ensures that authorities are only loaded when you explicitly access
     them (improves performance).
 */
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@Builder
//@Entity
//@Table(name = "user_tbl")
//@Component
//public class User {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(name = "username", nullable = false, unique = true)
//    @NotBlank(message = "User name cannot be blank")
//    private String username;
//
//    @Column(name = "password", nullable = false)
//    @NotBlank(message = "Password cannot be blank")
//    private String password;
//
//    @Column(name = "enabled")
//    private boolean enabled; // admin can set a user as enabled or disabled
//
//    // One-to-many relationship with Authority entity
//    //@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//
//    private List<Authority> authorities;  // The list of associated authorities
//}

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "user_tbl")
@Component
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    @NotBlank(message = "User name cannot be blank")
    private String username;

    @Column(name = "password", nullable = false)
    @NotBlank(message = "Password cannot be blank")
    private String password;

    @Column(name = "enabled")
    private boolean enabled; // admin can set a user as enabled or disabled

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore // Add this to prevent loading the authorities in the response by default
    private List<Authority> authorities;  // The list of associated authorities

    // Exclude authorities from toString
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", enabled=" + enabled +
                '}';
    }
}

