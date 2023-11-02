package com.rabin.onlinebookstore.repository;

import com.rabin.onlinebookstore.model.Users;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository <Users, Integer> {
}
