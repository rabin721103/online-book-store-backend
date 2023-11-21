package com.rabin.onlinebookstore.repository;

import com.rabin.onlinebookstore.model.Users;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository <Users, Integer> {
    Optional<Users> findUsersByUsername(String username);
}
