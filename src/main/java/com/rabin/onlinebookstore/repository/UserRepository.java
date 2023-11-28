package com.rabin.onlinebookstore.repository;

import com.rabin.onlinebookstore.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository <Users, Integer> {
    Optional<Users> findUsersByUsername(String username);

    @Query(value = "SELECT * FROM users WHERE username = :username AND password = :password", nativeQuery = true)
    Users findUserByUsernameAndPassword(String username, String password);
}
