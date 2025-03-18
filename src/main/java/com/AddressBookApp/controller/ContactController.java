package com.AddressBookApp.controller;

import com.AddressBookApp.dto.ContactDTO;
import com.AddressBookApp.model.Contact;
import com.AddressBookApp.service.ContactService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contacts")
@Tag(name = "Contact API", description = "Operations related to contacts in the Address Book")
public class ContactController {

    @Autowired
    private ContactService contactService;

    // ✅ Create a new contact
    @PostMapping("/add")
    @Operation(summary = "Create a new contact", description = "Adds a new contact to the address book")
    public Contact createContact(@RequestBody ContactDTO contactDTO) {
        return contactService.createAddressBookEntry(contactDTO);
    }

    // ✅ Fetch all contacts
    @GetMapping
    @Operation(summary = "Get all contacts", description = "Fetches all contacts from the address book")
    public List<Contact> getAllContacts() {
        return contactService.getAllEntries();
    }

    // ✅ Fetch contact by ID
    @GetMapping("/{id}")
    @Operation(summary = "Get contact by ID", description = "Fetches a contact by its ID")
    public Contact getContactById(@PathVariable Long id) {
        return contactService.getEntryById(id);
    }

    // ✅ Update an existing contact
    @PutMapping("/update/{id}")
    @Operation(summary = "Update an existing contact", description = "Updates the details of an existing contact")
    public Contact updateContact(@PathVariable Long id, @RequestBody ContactDTO contactDTO) {
        return contactService.updateEntry(id, contactDTO);
    }

    // ✅ Delete a contact
    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete a contact", description = "Deletes a contact by its ID")
    public void deleteContact(@PathVariable Long id) {
        contactService.deleteEntry(id);
    }
}
