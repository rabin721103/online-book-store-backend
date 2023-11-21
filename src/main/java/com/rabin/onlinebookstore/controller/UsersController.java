package com.rabin.onlinebookstore.controller;

import com.rabin.onlinebookstore.utils.ResponseWrapper;
import com.rabin.onlinebookstore.model.Users;
import com.rabin.onlinebookstore.service.UsersService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UsersController {
    @Autowired
    UsersService usersService;
    @PostMapping("/")
    private ResponseEntity<ResponseWrapper> saveUser(@Valid @RequestBody Users users) {
        usersService.saveUser(users);
        ResponseWrapper response = new ResponseWrapper();
        response.setStatusCode(HttpStatus.CREATED.value());
        response.setMessage("User created successfully");
        response.setResponse(users);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @GetMapping("/")
    private ResponseEntity<ResponseWrapper> getAllUser() {
        usersService.getAllUsers();
        ResponseWrapper response = new ResponseWrapper();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Users retrieved successfully");
        response.setResponse(usersService.getAllUsers());
        return ResponseEntity.ok(response);
    }
    @GetMapping("/{userId}")
    private ResponseEntity<ResponseWrapper> getUsersById(@PathVariable int userId) {
        Users user = usersService.getUsersById(userId);
        if (user != null) {
            ResponseWrapper response = new ResponseWrapper();
            response.setStatusCode(HttpStatus.OK.value());
            response.setMessage("User retrieved successfully");
            response.setResponse(user);
            return ResponseEntity.ok(response);
        } else {
            ResponseWrapper response = new ResponseWrapper();
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            response.setMessage("User not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
    @PutMapping("/{id}")
    private ResponseEntity<ResponseWrapper> updateUser(@PathVariable int id, @Valid @RequestBody Users updatedUsers) {
        Users optionalUsers = usersService.updateUser(id, updatedUsers);
        if (optionalUsers != null) {
            ResponseWrapper response = new ResponseWrapper();
            response.setStatusCode(HttpStatus.OK.value());
            response.setMessage("User updated successfully");
            response.setResponse(optionalUsers);
            return ResponseEntity.ok(response);
        } else {
            ResponseWrapper response = new ResponseWrapper();
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            response.setMessage("User not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
    @DeleteMapping("/{id}")
    private ResponseEntity<ResponseWrapper> deleteUser(@PathVariable("id") int id) {
        usersService.deleteUser(id);
        ResponseWrapper response = new ResponseWrapper();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("User deleted successfully");
        return ResponseEntity.ok(response);
    }
}

