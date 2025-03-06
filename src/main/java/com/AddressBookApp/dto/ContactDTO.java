package com.AddressBookApp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContactDTO {
    private Long id;
    private String name;
    private String phone;
    private String email;
    private String address;
}
