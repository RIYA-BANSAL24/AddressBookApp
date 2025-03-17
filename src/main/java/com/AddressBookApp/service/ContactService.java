package com.AddressBookApp.service;

import com.AddressBookApp.dto.ContactDTO;
import com.AddressBookApp.model.Contact;
import com.AddressBookApp.repository.ContactRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.Optional;

@Service
public class ContactService {

    private static final Logger log = LoggerFactory.getLogger(ContactService.class);
    private final ContactRepository contactRepository;

    // Constructor-based dependency injection
    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    // Cache contacts list
    @Cacheable(value = "contacts")
    public List<Contact> getAllEntries() {
        List<Contact> contacts = contactRepository.findAll();

        if (contacts.isEmpty()) {
            log.warn("⚠️ No contacts found in the database!");
        } else {
            log.info("✅ Fetched {} contacts: {}", contacts.size(), contacts);
        }

        return contacts;
    }

    // Cache individual contact by ID
    @Cacheable(value = "contact", key = "#id")
    public Contact getEntryById(Long id) {
        Optional<Contact> contact = contactRepository.findById(id);

        if (contact.isPresent()) {
            log.info("✅ Found contact: {}", contact.get());
        } else {
            log.warn("⚠️ No contact found with ID: {}", id);
        }

        return contact.orElse(null);
    }

    // Create a new contact and update cache
    @CachePut(value = "contact", key = "#result.id")
    public Contact createAddressBookEntry(ContactDTO dto) {
        Contact contact = new Contact();
        contact.setName(dto.getName());
        contact.setEmail(dto.getEmail());
        contact.setNumber(dto.getPhoneNumber());

        Contact savedContact = contactRepository.save(contact);
        log.info("Created new contact: {}", savedContact);
        return savedContact;
    }

    // Update existing contact and update cache
    @CachePut(value = "contact", key = "#id")
    public Contact updateEntry(Long id, ContactDTO dto) {
        Optional<Contact> existingContact = contactRepository.findById(id);
        if (existingContact.isPresent()) {
            Contact contact = existingContact.get();
            contact.setName(dto.getName());
            contact.setEmail(dto.getEmail());
            contact.setNumber(dto.getPhoneNumber());

            Contact updatedContact = contactRepository.save(contact);
            log.info("Updated contact with ID {}: {}", id, updatedContact);
            return updatedContact;
        }

        log.warn("⚠️ Cannot update: No contact found with ID {}", id);
        return null;
    }

    // Delete contact and remove from cache
    @CacheEvict(value = "contact", key = "#id", allEntries = true)
    public void deleteEntry(Long id) {
        if (contactRepository.existsById(id)) {
            contactRepository.deleteById(id);
            log.info("Deleted contact with ID: {}", id);
        }
        else {
            log.warn("Cannot delete: No contact found with ID {}", id);
        }
    }
}
