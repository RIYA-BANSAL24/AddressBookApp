package com.AddressBookApp.service;


import com.AddressBookApp.model.Contact;
import com.AddressBookApp.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContactService {

    // to handle database operations
    @Autowired
    private ContactRepository contactRepository;

    public Contact addContact(Contact contact) {
        return contactRepository.save(contact);
    }

    public Contact getContactById(Long id) {
        return contactRepository.findById(id).orElse(null);
    }

    public Contact updateContact(Long id, Contact updatedContact) {
        if (contactRepository.existsById(id)) {
            updatedContact.setId(id);
            return contactRepository.save(updatedContact);
        }
        return null;
    }

    public boolean deleteContact(Long id) {
        if (contactRepository.existsById(id)) {
            contactRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
