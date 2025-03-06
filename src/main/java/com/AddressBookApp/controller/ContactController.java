package com.AddressBookApp.controller;

//import com.AddressBookApp.dto.ContactDTO;
import com.AddressBookApp.model.Contact;
//import com.AddressBookApp.repository.ContactRepository;
import com.AddressBookApp.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//import java.util.List;
//import java.util.stream.Collectors;
//
//@RestController
//@RequestMapping("/contact")
//public class ContactController {
//    //private final ContactRepository repository;
//
//    @Autowired
//    private ContactService contactService;
//
////    public ContactController(ContactRepository repository) {
////        this.repository = repository;
////    }
//
////    // Convert Model to DTO
////    private ContactDTO convertToDTO(Contact contact) {
////        ContactDTO dto = new ContactDTO();
////        dto.setId(contact.getId());
////        dto.setName(contact.getName());
////        dto.setPhone(contact.getPhone());
////        dto.setEmail(contact.getEmail());
////        dto.setAddress(contact.getAddress());
////        return dto;
////    }
//
//    // Add Contact
//    @PostMapping("/add")
////    public ResponseEntity<ContactDTO> addContact(@RequestBody ContactDTO contactDTO) {
////        Contact contact = new Contact();
////        contact.setName(contactDTO.getName());
////        contact.setPhone(contactDTO.getPhone());
////        contact.setEmail(contactDTO.getEmail());
////        contact.setAddress(contactDTO.getAddress());
////
////        Contact savedContact = repository.save(contact);
////        ContactDTO savedContactDTO = convertToDTO(savedContact);
////        return ResponseEntity.ok(savedContactDTO);
////    }
//    public Contact addContact(@RequestBody Contact contact) {
//        return contactService.addContact(contact);
//    }
//
//
//    // Get All Contacts
//    @GetMapping("/show")
////    public ResponseEntity<List<ContactDTO>> getAllContacts() {
////        List<Contact> contacts = repository.findAll();
////        List<ContactDTO> contactDTOs = contacts.stream()
////                .map(this::convertToDTO)
////                .collect(Collectors.toList());
////        return ResponseEntity.ok(contactDTOs);
////    }
//
//    public Contact getContact(@PathVariable Long id) {
//        return contactService.getContactById(id);
//    }
//
////    // Get Contact by ID
////    @GetMapping("/{id}")
////    public ResponseEntity<ContactDTO> getContactById(@PathVariable Long id) {
////        return repository.findById(id)
////                .map(contact -> ResponseEntity.ok(convertToDTO(contact)))
////                .orElseGet(() -> ResponseEntity.notFound().build());
////    }
//
//    // Update Contact by ID
//    @PutMapping("/update/{id}")
////    public ResponseEntity<ContactDTO> updateContact(@PathVariable Long id, @RequestBody ContactDTO contactDTO) {
////        return repository.findById(id)
////                .map(contact -> {
////                    contact.setName(contactDTO.getName());
////                    contact.setPhone(contactDTO.getPhone());
////                    contact.setEmail(contactDTO.getEmail());
////                    contact.setAddress(contactDTO.getAddress());
////                    Contact updatedContact = repository.save(contact);
////                    return ResponseEntity.ok(convertToDTO(updatedContact));
////                })
////                .orElseGet(() -> ResponseEntity.notFound().build());
////    }
//
//    public Contact updateContact(@PathVariable Long id, @RequestBody Contact updatedContact) {
//        return contactService.updateContact(id, updatedContact);
//    }
//
//
//    // Delete Contact
//    @DeleteMapping("/delete/{id}")
////    public ResponseEntity<Void> deleteContact(@PathVariable Long id) {
////        if (!repository.existsById(id)) {
////            return ResponseEntity.notFound().build();
////        }
////        repository.deleteById(id);
////        return ResponseEntity.noContent().build();
////    }
//
//    public boolean deleteContact(@PathVariable Long id) {
//        return contactService.deleteContact(id);
//    }
//}


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
