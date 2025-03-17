package com.AddressBookApp.service;

import com.AddressBookApp.dto.UserDTO;
import com.AddressBookApp.model.User;
import com.AddressBookApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public User registerUser(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());  // Set username
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPhone(userDTO.getPhone());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        return userRepository.save(user);
    }


    public String loginUser(UserDTO userDTO) {
        Optional<User> userOptional = userRepository.findByEmail(userDTO.getEmail());

        if (userOptional.isPresent() &&
                passwordEncoder.matches(userDTO.getPassword(), userOptional.get().getPassword())) {
            return "Login successful!"; // In real-world, return JWT token
        } else {
            return "Invalid email or password";
        }
    }
}

