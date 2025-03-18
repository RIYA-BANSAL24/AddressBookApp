package com.AddressBookApp.service;

import com.AddressBookApp.dto.UserDTO;
import com.AddressBookApp.model.User;
import com.AddressBookApp.repository.UserRepository;
import com.AddressBookApp.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private EmailService emailService;

    // ✅ User Registration
    public String registerUser(UserDTO userDTO) {
        try {
            if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
                return "Error: Email is already registered!";
            }

            User user = new User();
            user.setName(userDTO.getName());
            user.setEmail(userDTO.getEmail());
            user.setPhone(userDTO.getPhone());
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

            userRepository.save(user);

            String token = jwtUtil.generateToken(user.getEmail());

            String emailBody = "Welcome " + user.getName() + ",\n\nYour account has been successfully registered.\n\nToken: " + token;
            emailService.sendEmail(user.getEmail(), "Registration Successful", emailBody);

            return "User registered successfully! Token: " + token;

        } catch (Exception e) {
            return "Error during registration: " + e.getMessage();
        }
    }

    public String loginUser(UserDTO userDTO) {
        try {
            Optional<User> userOptional = userRepository.findByEmail(userDTO.getEmail());

            if (userOptional.isEmpty()) {
                return "No user found with email: " + userDTO.getEmail();
            }

            User user = userOptional.get();

            if (!passwordEncoder.matches(userDTO.getPassword(), user.getPassword())) {
                return "Incorrect password for email: " + userDTO.getEmail();
            }

            // 🔹 Generate JWT Token on successful login
            return "Login successful! Token: " + jwtUtil.generateToken(user.getEmail());

        } catch (Exception e) {
            return "Error during login: " + e.getMessage();
        }
    }
}