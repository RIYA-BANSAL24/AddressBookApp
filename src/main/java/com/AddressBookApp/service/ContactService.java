package com.AddressBookApp.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.AddressBookApp.dto.ContactDTO;
import com.AddressBookApp.model.Contact;
import com.AddressBookApp.repository.ContactRepository;
import org.springframework.context.ApplicationContext;

import java.util.List;
import java.util.Optional;

@Slf4j
@SpringBootApplication
public class ContactService {

    ContactRepository contactRepository;

    public List<Contact> getAllEntries() {
        return contactRepository.findAll();
    }

    public Contact getEntryById(Long id) {
        return contactRepository.findById(id).orElse(null);
    }

    public Contact createAddressBookEntry(ContactDTO dto) {
        Contact contact = new Contact();
        contact.setName(dto.getName());
        contact.setEmail(dto.getEmail());
        contact.setPhoneNumber(dto.getPhoneNumber());
        return contactRepository.save(contact);
    }

    public Contact updateEntry(Long id, ContactDTO dto) {
        Optional<Contact> existingContact = contactRepository.findById(id);
        if (existingContact.isPresent()) {
            Contact contact = existingContact.get();
            contact.setName(dto.getName());
            contact.setEmail(dto.getEmail());
            contact.setPhoneNumber(dto.getPhoneNumber());
            return contactRepository.save(contact);
        }
        return null; // Handle not found case properly in controller
    }

    public void deleteEntry(Long id) {
        if (contactRepository.existsById(id)) {
            contactRepository.deleteById(id);
        }
    }
}