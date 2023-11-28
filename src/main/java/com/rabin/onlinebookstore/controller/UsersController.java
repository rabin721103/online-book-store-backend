package com.rabin.onlinebookstore.controller;

import com.rabin.onlinebookstore.utils.JwtService;
import com.rabin.onlinebookstore.utils.ResponseWrapper;
import com.rabin.onlinebookstore.model.Users;
import com.rabin.onlinebookstore.service.UsersService;
import jakarta.servlet.http.HttpServletRequest;
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
    @PostMapping("/auth/register")
    private ResponseWrapper registerUser(@Valid @RequestBody Users users) {
        usersService.saveUser(users);
        ResponseWrapper response = new ResponseWrapper();
        response.setStatusCode(HttpStatus.CREATED.value());
        response.setMessage("User created successfully");
        response.setResponse(users);
        response.setSuccess(true);
        return response;
    }

    @PostMapping("/auth/login")
    private ResponseWrapper loginUser(@Valid @RequestBody Users users) {
        Users user = usersService.logIn(users.getUsername(), users.getPassword());

        if(user != null){
            JwtService jwtService = new JwtService();
            String token = jwtService.generateToken(user);
            return new ResponseWrapper(true, 200, "Login Success", token);
        }else{
            return new ResponseWrapper(false, 400, "User not found ", null);
        }
    }



    @GetMapping("/admin/users")
    private ResponseEntity<ResponseWrapper> getAllUser() {
        usersService.getAllUsers();
        ResponseWrapper response = new ResponseWrapper();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Users retrieved successfully");
        response.setResponse(usersService.getAllUsers());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/profile")
    private ResponseWrapper getProfile(HttpServletRequest request) {
        int userId = (int) request.getAttribute("userId");
        Users user = usersService.getUsersById(userId);
        if (user != null) {
            ResponseWrapper response = new ResponseWrapper();
            response.setStatusCode(HttpStatus.OK.value());
            response.setMessage("User retrieved successfully");
            response.setSuccess(true);
            response.setResponse(user);
            return response;
        } else {
            ResponseWrapper response = new ResponseWrapper();
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            response.setMessage("User not found");
            response.setSuccess(false);
            return response;
        }
    }

    @GetMapping("/admin/users/{userId}")
    private ResponseWrapper getUsersById(@PathVariable int userId) {
        Users user = usersService.getUsersById(userId);
        if (user != null) {
            ResponseWrapper response = new ResponseWrapper();
            response.setStatusCode(HttpStatus.OK.value());
            response.setMessage("User retrieved successfully");
            response.setResponse(user);
            return response;
        } else {
            ResponseWrapper response = new ResponseWrapper();
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            response.setMessage("User not found");
            return response;
        }
    }

    @PutMapping("/admin/users/{id}")
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
    @DeleteMapping("/admin/users/{id}")
    private ResponseEntity<ResponseWrapper> deleteUser(@PathVariable("id") int id) {
        usersService.deleteUser(id);
        ResponseWrapper response = new ResponseWrapper();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("User deleted successfully");
        return ResponseEntity.ok(response);
    }
}

