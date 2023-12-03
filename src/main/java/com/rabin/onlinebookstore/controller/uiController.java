//package com.rabin.onlinebookstore.controller;
//
//import com.rabin.onlinebookstore.model.Users;
//import com.rabin.onlinebookstore.service.UsersService;
//import jakarta.validation.Valid;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@Controller
//public class uiController {
//    @Autowired
//    private UsersService usersService;
//
//    @GetMapping("/")
//    public String viewHome (Model model){
//        List<Users> users = usersService.getAllUsers();
//        model.addAttribute("allUsers",users);
//        return "index";
//    }
//    @GetMapping("/addUsers")
//    public String addNewUser(Model model){
//        Users users = new Users();
//        model.addAttribute("users",users);
//        return "addUser";
//    }
//    @PostMapping("/save")
//    public String saveUser(@Valid @ModelAttribute ("users")Users users, BindingResult result){
//        if (result.hasErrors()){
//            return "addUser";
//        }
//        usersService.saveUser(users);
//        return "redirect:/";
//    }
//
//    @GetMapping("/update/{id}")
//    public String updateUser( @PathVariable ("id")int userId, @Valid Model model){
//        Users users = usersService.getUsersById(userId);
//        model.addAttribute("users",users);
//        return "update";
//    }
//    @PostMapping("/update/{id}")
//    public String update( @PathVariable("id")int userId,@Valid @ModelAttribute ("users")Users users, BindingResult result){
//        if (result.hasErrors()){
//            return "update";
//        }
//        usersService.updateUser(userId, users);
//        return "redirect:/";
//    }
//    @GetMapping("/delete/{id}")
//    public String deleteByID(@PathVariable ("id")int userId){
//        usersService.deleteUser(userId);
//        return "redirect:/";
//    }
//}
