package com.AddressBookApp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ContactDTO {

<<<<<<< HEAD
    private String name;

    private String email;

=======

    @NotBlank(message = "Name cannot be blank")
    private String name;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email cannot be blank")
    private String email;

    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be 10 digits")
    private String phoneNumber;
>>>>>>> fb8ad4274f09589816b9f68edc57b5823f4b45ee
}
