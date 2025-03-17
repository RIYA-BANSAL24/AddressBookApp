package com.AddressBookApp.controller;

import com.AddressBookApp.dto.UserDTO;
import com.AddressBookApp.model.User;
import com.AddressBookApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")  // Ensure this matches SecurityConfig
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody UserDTO userDTO) {
        User registeredUser = userService.registerUser(userDTO);
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody UserDTO userDTO) {
        String response = userService.loginUser(userDTO);
        return ResponseEntity.ok(response);
    }
}
