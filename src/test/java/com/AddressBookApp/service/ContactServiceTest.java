package com.AddressBookApp.service;

import com.AddressBookApp.dto.ContactDTO;
import com.AddressBookApp.model.Contact;
import com.AddressBookApp.repository.ContactRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContactServiceTest {

    @Mock
    private ContactRepository contactRepository;

    @InjectMocks
    private ContactService contactService;

    private Contact contact;
    private ContactDTO contactDTO;

    @BeforeEach
    void setUp() {
        contact = new Contact();
        contact.setId(1L);
        contact.setName("John Doe");
        contact.setEmail("john.doe@example.com");
        contact.setNumber("1234567890");

        contactDTO = new ContactDTO();
        contactDTO.setName("John Doe");
        contactDTO.setEmail("john.doe@example.com");
        contactDTO.setPhoneNumber("1234567890");
    }

    // ✅ Test: Get all contacts
    @Test
    void testGetAllEntries() {
        List<Contact> contactList = Arrays.asList(contact);
        when(contactRepository.findAll()).thenReturn(contactList);

        List<Contact> result = contactService.getAllEntries();

        assertEquals(1, result.size());
        assertEquals("John Doe", result.get(0).getName());
        verify(contactRepository, times(1)).findAll();
    }

    // ✅ Test: Get contact by ID (Valid ID)
    @Test
    void testGetEntryById_WhenContactExists() {
        when(contactRepository.findById(1L)).thenReturn(Optional.of(contact));

        Contact result = contactService.getEntryById(1L);

        assertNotNull(result);
        assertEquals("John Doe", result.getName());
        verify(contactRepository, times(1)).findById(1L);
    }

    // ✅ Test: Get contact by ID (Invalid ID)
    @Test
    void testGetEntryById_WhenContactDoesNotExist() {
        when(contactRepository.findById(1L)).thenReturn(Optional.empty());

        Contact result = contactService.getEntryById(1L);

        assertNull(result);
        verify(contactRepository, times(1)).findById(1L);
    }

    // ✅ Test: Create a new contact
    @Test
    void testCreateAddressBookEntry() {
        when(contactRepository.save(Mockito.any(Contact.class))).thenReturn(contact);

        Contact result = contactService.createAddressBookEntry(contactDTO);

        assertNotNull(result);
        assertEquals("John Doe", result.getName());
        assertEquals("john.doe@example.com", result.getEmail());
        verify(contactRepository, times(1)).save(Mockito.any(Contact.class));
    }

    // ✅ Test: Update existing contact (Valid ID)
    @Test
    void testUpdateEntry_WhenContactExists() {
        when(contactRepository.findById(1L)).thenReturn(Optional.of(contact));
        when(contactRepository.save(Mockito.any(Contact.class))).thenReturn(contact);

        ContactDTO updatedDTO = new ContactDTO("Jane Doe", "jane.doe@example.com", "9876543210");
        Contact updatedContact = contactService.updateEntry(1L, updatedDTO);

        assertNotNull(updatedContact);
        assertEquals("Jane Doe", updatedContact.getName());
        assertEquals("jane.doe@example.com", updatedContact.getEmail());
        verify(contactRepository, times(1)).findById(1L);
        verify(contactRepository, times(1)).save(Mockito.any(Contact.class));
    }

    // ✅ Test: Update contact (Invalid ID)
    @Test
    void testUpdateEntry_WhenContactDoesNotExist() {
        when(contactRepository.findById(1L)).thenReturn(Optional.empty());

        ContactDTO updatedDTO = new ContactDTO("Jane Doe", "jane.doe@example.com", "9876543210");
        Contact result = contactService.updateEntry(1L, updatedDTO);

        assertNull(result);
        verify(contactRepository, times(1)).findById(1L);
        verify(contactRepository, times(0)).save(Mockito.any(Contact.class));
    }

    // ✅ Test: Delete existing contact
    @Test
    void testDeleteEntry_WhenContactExists() {
        when(contactRepository.existsById(1L)).thenReturn(true);
        doNothing().when(contactRepository).deleteById(1L);

        contactService.deleteEntry(1L);

        verify(contactRepository, times(1)).existsById(1L);
        verify(contactRepository, times(1)).deleteById(1L);
    }

    // ✅ Test: Delete contact (Invalid ID)
    @Test
    void testDeleteEntry_WhenContactDoesNotExist() {
        when(contactRepository.existsById(1L)).thenReturn(false);

        contactService.deleteEntry(1L);

        verify(contactRepository, times(1)).existsById(1L);
        verify(contactRepository, times(0)).deleteById(1L);
    }
}
