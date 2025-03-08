package com.AddressBookApp.controller;

//import com.AddressBookApp.dto.ContactDTO;
import com.AddressBookApp.model.Contact;
//import com.AddressBookApp.repository.ContactRepository;
import com.AddressBookApp.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/contact")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @GetMapping
    public List<Contact> getAllContacts() {
        return contactService.getAllContacts();
    }

    // Get contact by ID
    @GetMapping("/{id}")
    public ResponseEntity<Contact> getContactById(@PathVariable Long id) {
        Optional<Contact> contact = contactService.getContactById(id);
        return contact.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping("/create")
    public ResponseEntity<Contact> createContact(@RequestBody Contact contact) {
        Contact createdContact = contactService.createContact(contact);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdContact);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Contact> updateContact(@PathVariable Long id, @RequestBody Contact contact) {
        Optional<Contact> updatedContact = contactService.updateContact(id, contact);
        return updatedContact.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteContact(@PathVariable Long id) {
        boolean isDeleted = contactService.deleteContact(id);
        return isDeleted ? ResponseEntity.noContent().build() : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
