package com.AddressBookApp.model;

import jakarta.persistence.*;
import lombok.*;
@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // ✅ Ensure auto-increment works
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String password;

}
