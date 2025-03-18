package com.AddressBookApp.controller;

import com.AddressBookApp.dto.UserDTO;
import com.AddressBookApp.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@Tag(name = "User API", description = "Operations related to user management")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    @Operation(summary = "Register a new user", description = "Creates a new user account with encrypted password")
    public String registerUser(@RequestBody UserDTO userDTO) {
        try {
            userService.registerUser(userDTO);
            return "User registered successfully!";
        } catch (Exception e) {
            return "Error during registration: " + e.getMessage();
        }
    }

    @PostMapping("/login")
    @Operation(summary = "User Login", description = "Validates user credentials and returns login status")
    public String loginUser(@RequestBody UserDTO userDTO) {
        return userService.loginUser(userDTO);
    }
}