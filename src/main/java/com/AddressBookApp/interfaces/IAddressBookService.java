package com.AddressBookApp.interfaces;

import com.AddressBookApp.dto.ContactDTO;
import com.AddressBookApp.model.Contact;

import java.util.List;

public interface IAddressBookService {
    Contact createAddressBookEntry(ContactDTO dto);
    List<Contact> getAllEntries();
    Contact getEntryById(Long id);
    Contact updateEntry(Long id, ContactDTO dto);
    void deleteEntry(Long id);
}
