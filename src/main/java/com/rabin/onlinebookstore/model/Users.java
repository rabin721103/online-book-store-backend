package com.rabin.onlinebookstore.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users")
public class Users {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int userId;

    @Column (nullable = false)
    @NotEmpty
    @Size(min = 2, message = "user name should have at least 2 characters")
    @Pattern(regexp = "^[a-zA-Z0-9_-]{3,20}$", message = "Invalid Username...should not contain space")
    private String username;

    @Column(nullable = false)
    @NotEmpty
    @Size(min = 5, message = "password should have at least 5 characters")
    private String password;

    @Column (nullable = false)
    private String email;

    @Column
    private String role;

}
