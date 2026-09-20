package com.contact_manager.service;

import com.contact_manager.entity.Contact;
import com.contact_manager.exception.ContactNotFoundException;
import com.contact_manager.repository.ContactRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ContactService {

    private final ContactRepository contactRepository;

    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }
    // add contact
    public Contact addContact(Contact contact){
        return contactRepository.save(contact);
    }
    //Read all contact
    public List<Contact> getAllContacts() {
        return contactRepository.findAll();
    }

    //get contact by id
    public Contact getContactById(Long id) {
        return contactRepository.findById(id).orElseThrow(() -> new ContactNotFoundException("Contact not found with id "+id));

    }

//    update contact by id
    public Contact updateContactById(Long id, Contact contact) {
        Contact existingContact = contactRepository.findById(id).orElseThrow(() -> new ContactNotFoundException("Contact not found with id " +id));

        existingContact.setFirstName(contact.getFirstName());
        existingContact.setLastName(contact.getLastName());
        existingContact.setEmail(contact.getEmail());
        existingContact.setPhoneNumber(contact.getPhoneNumber());
        existingContact.setAddress((contact.getAddress()));

        return contactRepository.save(existingContact);
    }
//    delete contact by id
    public void deleteContactById(Long id) {
        Contact contact = contactRepository.findById(id).orElseThrow(() -> new ContactNotFoundException("Contact not found with id " + id));
        contactRepository.delete(contact);
    }
}


