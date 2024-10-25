package com.cwc.ExceptionHandling_Validation_Security.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 username and authority: These fields correspond to the columns in the authorities table and store the
 username and the role/authority, respectively.
 @ManyToOne relationship: The Authority entity is linked to the User entity via a ManyToOne relationship.
 This means that each Authority record belongs to a single user. We define the relationship by linking
 the username field in the Authority entity to the username field in the User entity.
 */

//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@Builder
//@Entity //This tells JPA that this class represents an entity and is mapped to a table in the database.
//@Table(name = "authorities")
//
//public class Authority {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY) // Automatically generate ID
//    private Long id;
//
//    @Column(name = "username", nullable = false)
//    private String username;
//
//    @Column(name = "authority", nullable = false)
//    private String authority;
//
//    // Relationship with User entity
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "username", referencedColumnName = "username", insertable = false, updatable = false)
//    @JsonIgnore  // This will prevent serialization of the User field in Authority
//
//    private User user; // The user that this authority belongs to
//
//}

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "authorities")
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Automatically generate ID
    private Long id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "authority", nullable = false)
    private String authority;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "username", referencedColumnName = "username", insertable = false, updatable = false)
    @JsonIgnore
    private User user;

    @Override
    public String toString() {
        return "Authority{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", authority='" + authority + '\'' +
                '}';
    }
}
