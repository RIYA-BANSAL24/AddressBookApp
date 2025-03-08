package com.AddressBookApp.service;

import com.AddressBookApp.dto.ContactDTO;
import com.AddressBookApp.model.Contact;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ContactService {

    List<Contact> contacts = new ArrayList<>();
    private long idCounter = 1; //auto-increment ID

    public List<Contact> getAllContacts() {
        log.info("Fetching all contacts");
        return contacts;
    }

    public Contact getContactById(Long id) {
        log.info("Fetching contact with ID: {}", id);
        return contacts.stream()
                .filter(contact -> contact.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> {
                    log.error("Contact with ID {} not found", id);
                    return new RuntimeException("Contact not found");
                });
    }

    public Contact addContact(Contact contact) {
        contact.setId(idCounter++);
        contacts.add(contact);
        log.info("Added new contact: {}", contact);
        return contact;
    }

    public Contact updateContact(Long id, Contact newContact) {
        Optional<Contact> optionalContact = contacts.stream()
                .filter(contact -> contact.getId().equals(id))
                .findFirst();

        if (optionalContact.isPresent()) {
            Contact existingContact = optionalContact.get();
            existingContact.setName(newContact.getName());
            existingContact.setEmail(newContact.getEmail());
            existingContact.setPhone(newContact.getPhone());
            log.info("Updated contact with ID {}: {}", id, existingContact);
            return existingContact;
        } else {
            log.error("Contact with ID {} not found for update", id);
            throw new RuntimeException("Contact not found");
        }
    }

    public void deleteContact(Long id) {
        contacts.removeIf(contact -> contact.getId().equals(id));
        log.warn("Deleted contact with ID: {}", id);
    }
}