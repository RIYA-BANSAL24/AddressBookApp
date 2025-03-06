package com.AddressBookApp.service;


import com.AddressBookApp.model.Contact;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ContactService {

    // In-memory storage for contacts
    private List<Contact> contactList = new ArrayList<>();

    public List<Contact> getAllContacts() {
        return contactList;
    }

    public Optional<Contact> getContactById(Long id) {
        return contactList.stream().filter(contact -> contact.getId().equals(id)).findFirst();
    }

    public Contact createContact(Contact contact) {
        contactList.add(contact);
        return contact;
    }

    public Optional<Contact> updateContact(Long id, Contact contact) {
        Optional<Contact> existingContact = getContactById(id);
        if (existingContact.isPresent()) {
            contact.setId(id);
            contactList.remove(existingContact.get());
            contactList.add(contact);
            return Optional.of(contact);
        }
        return Optional.empty();
    }

    public boolean deleteContact(Long id) {
        Optional<Contact> existingContact = getContactById(id);
        if (existingContact.isPresent()) {
            contactList.remove(existingContact.get());
            return true;
        }
        return false;
    }
}
