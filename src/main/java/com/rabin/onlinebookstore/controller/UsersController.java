package com.rabin.onlinebookstore.controller;

import com.rabin.onlinebookstore.config.JwtService;
import com.rabin.onlinebookstore.utils.ResponseWrapper;
import com.rabin.onlinebookstore.model.Users;
import com.rabin.onlinebookstore.service.UsersService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UsersController {
    @Autowired
    UsersService usersService;
    @PostMapping("/auth/register")
    public ResponseWrapper registerUser(@Valid @RequestBody Users users) {
        usersService.saveUser(users);
        ResponseWrapper response = new ResponseWrapper();
        response.setStatusCode(HttpStatus.CREATED.value());
        response.setMessage("User created successfully");
        response.setResponse(users);
        response.setSuccess(true);
        return response;
    }

    @PostMapping("/auth/login")
    public ResponseWrapper loginUser(@Valid @RequestBody Users users, HttpServletResponse response) {
        Users user = usersService.logIn(users.getUsername(), users.getPassword());

        if(user != null){
            JwtService jwtService = new JwtService();
            String token = jwtService.generateToken(user);
            final Cookie cookie = new Cookie("token", token);
            cookie.setSecure(false);
            cookie.setHttpOnly(true);
            cookie.setMaxAge(50400);
            cookie.setPath("/api");
            response.addCookie(cookie);
            return new ResponseWrapper(true, 200, "Login Success", token);
        }else{
            return new ResponseWrapper(false, 400, "User not found ", null);
        }
    }
    @GetMapping("/admin/users")
    public ResponseWrapper getAllUser() {
        usersService.getAllUsers();
        ResponseWrapper response = new ResponseWrapper();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Users retrieved successfully");
        response.setResponse(usersService.getAllUsers());
        return response;
    }
    @GetMapping("/profile")
    public ResponseWrapper getProfile(HttpServletRequest request) {
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
    public ResponseWrapper getUsersById(@PathVariable int userId) {
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
    public ResponseWrapper updateUser(@PathVariable int id, @Valid @RequestBody Users updatedUsers) {
        Users optionalUsers = usersService.updateUser(id, updatedUsers);
        if (optionalUsers != null) {
            ResponseWrapper response = new ResponseWrapper();
            response.setStatusCode(HttpStatus.OK.value());
            response.setMessage("User updated successfully");
            response.setResponse(optionalUsers);
            return response;
        } else {
            ResponseWrapper response = new ResponseWrapper();
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            response.setMessage("User not found");
            return response;
        }
    }
    @DeleteMapping("/admin/users/{id}")
    public ResponseWrapper deleteUser(@PathVariable("id") int id) {
        usersService.deleteUser(id);
        ResponseWrapper response = new ResponseWrapper();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("User deleted successfully");
        return response;
    }
}

