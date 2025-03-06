package com.AddressBookApp.controller;

import com.AddressBookApp.dto.ContactDTO;
import com.AddressBookApp.model.Contact;
import com.AddressBookApp.repository.ContactRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/contact")
public class ContactController {
    private final ContactRepository repository;

    public ContactController(ContactRepository repository) {
        this.repository = repository;
    }

    // Convert Model to DTO
    private ContactDTO convertToDTO(Contact contact) {
        ContactDTO dto = new ContactDTO();
        dto.setId(contact.getId());
        dto.setName(contact.getName());
        dto.setPhone(contact.getPhone());
        dto.setEmail(contact.getEmail());
        dto.setAddress(contact.getAddress());
        return dto;
    }

    // Add Contact
    @PostMapping("/add")
    public ResponseEntity<ContactDTO> addContact(@RequestBody ContactDTO contactDTO) {
        Contact contact = new Contact();
        contact.setName(contactDTO.getName());
        contact.setPhone(contactDTO.getPhone());
        contact.setEmail(contactDTO.getEmail());
        contact.setAddress(contactDTO.getAddress());

        Contact savedContact = repository.save(contact);
        ContactDTO savedContactDTO = convertToDTO(savedContact);
        return ResponseEntity.ok(savedContactDTO);
    }

    // Get All Contacts
    @GetMapping("/show")
    public ResponseEntity<List<ContactDTO>> getAllContacts() {
        List<Contact> contacts = repository.findAll();
        List<ContactDTO> contactDTOs = contacts.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(contactDTOs);
    }

    // Get Contact by ID
    @GetMapping("/{id}")
    public ResponseEntity<ContactDTO> getContactById(@PathVariable Long id) {
        return repository.findById(id)
                .map(contact -> ResponseEntity.ok(convertToDTO(contact)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Update Contact by ID
    @PutMapping("/update/{id}")
    public ResponseEntity<ContactDTO> updateContact(@PathVariable Long id, @RequestBody ContactDTO contactDTO) {
        return repository.findById(id)
                .map(contact -> {
                    contact.setName(contactDTO.getName());
                    contact.setPhone(contactDTO.getPhone());
                    contact.setEmail(contactDTO.getEmail());
                    contact.setAddress(contactDTO.getAddress());
                    Contact updatedContact = repository.save(contact);
                    return ResponseEntity.ok(convertToDTO(updatedContact));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Delete Contact
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteContact(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
