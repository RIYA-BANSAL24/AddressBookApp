package com.AddressBookApp.service;

import com.AddressBookApp.dto.UserDTO;
import com.AddressBookApp.exception.DuplicateEmailException;
import com.AddressBookApp.exception.InvalidLoginException;
import com.AddressBookApp.exception.UserNotFoundException;
import com.AddressBookApp.model.User;
import com.AddressBookApp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // User Registration
    public User registerUser(UserDTO userDTO) {
        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new DuplicateEmailException("Email is already registered: " + userDTO.getEmail());
        }

        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPhone(userDTO.getPhone());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        return userRepository.save(user);
    }

    // User Login
    public String loginUser(UserDTO userDTO) {
        Optional<User> userOptional = userRepository.findByEmail(userDTO.getEmail());

        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("No user found with email: " + userDTO.getEmail());
        }

        if (!passwordEncoder.matches(userDTO.getPassword(), userOptional.get().getPassword())) {
            throw new InvalidLoginException("Incorrect password for email: " + userDTO.getEmail());
        }

        return "Login successful!";
    }
}
