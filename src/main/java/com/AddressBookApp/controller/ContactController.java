package com.AddressBookApp.controller;

import com.AddressBookApp.dto.ContactDTO;
import com.AddressBookApp.model.Contact;
import com.AddressBookApp.service.ContactService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contact")  // Base URL updated to /contact
public class ContactController {

    // Renamed service bean to be more descriptive
    @Autowired
    private ContactService contactService;  // Fixed variable name

    // Create a new contact entry
    @PostMapping("/create")
    public ResponseEntity<Contact> createEntry(@Valid @RequestBody ContactDTO dto) {
        // Calling service to create the entry
        Contact contact = contactService.createAddressBookEntry(dto);  // Fixed method call
        return ResponseEntity.ok(contact);
    }

    // Get all contact entries
    @GetMapping("/all")
    public ResponseEntity<List<Contact>> getAllEntries() {
        return ResponseEntity.ok(contactService.getAllEntries());  // Fixed method call
    }

    // Get contact entry by ID
    @GetMapping("/{id}")
    public ResponseEntity<Contact> getEntryById(@PathVariable Long id) {
        Contact contact = contactService.getEntryById(id);  // Fixed method call
        return (contact != null) ? ResponseEntity.ok(contact) : ResponseEntity.notFound().build();
    }

    // Update contact entry by ID
    @PutMapping("/update/{id}")
    public ResponseEntity<Contact> updateEntry(@PathVariable Long id, @Valid @RequestBody ContactDTO dto) {
        Contact updatedContact = contactService.updateEntry(id, dto);  // Fixed method call
        return (updatedContact != null) ? ResponseEntity.ok(updatedContact) : ResponseEntity.notFound().build();
    }

    // Delete contact entry by ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteEntry(@PathVariable Long id) {
        contactService.deleteEntry(id);  // Fixed method call
        return ResponseEntity.noContent().build();
    }
}