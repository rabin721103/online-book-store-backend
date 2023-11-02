package com.rabin.onlinebookstore.service;

import com.rabin.onlinebookstore.utils.ResponseWrapper;
import com.rabin.onlinebookstore.model.Users;
import com.rabin.onlinebookstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UsersService {
    @Autowired
    UserRepository userRepository;
    List <Users> users = new ArrayList<>();
    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }
    public Users getUsersById (int id){
        Optional<Users> optionalUsers = userRepository.findById(id);
        return optionalUsers.orElse(null);
    }
    public Users saveUser(Users users){
        return userRepository.save(users);
    }

    public Users updateUser(int id, Users updatedUser){
        Optional<Users> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            Users existingUser = optionalUser.get();

            // Update properties of the existing user with values from the updated user
            existingUser.setUserId(id);
            existingUser.setPassword(updatedUser.getPassword());
            existingUser.setUsername(updatedUser.getUsername());
            existingUser.setRole(updatedUser.getRole());
            existingUser.setEmail(updatedUser.getEmail());
            return userRepository.save(existingUser);

        }
        return null;
    }
    public ResponseWrapper deleteUser(int id){
        Optional<Users> optionalUsers = userRepository.findById(id);
        if (optionalUsers.isPresent()){
            userRepository.deleteById(id);
        }
        return null;
    }
}
