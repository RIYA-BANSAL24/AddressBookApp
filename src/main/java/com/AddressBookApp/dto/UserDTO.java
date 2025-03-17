package com.AddressBookApp.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String username;
    private String name;
    private String email;
    private String phone;
    private String password;
}
