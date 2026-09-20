package com.contact_manager.controller;

import com.contact_manager.entity.Contact;
import com.contact_manager.service.ContactService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/contact")
public class ContactController {

    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    //Retrive All Contacts
    @GetMapping
    public ResponseEntity<List<Contact>> getAllContacts(){
        List<Contact> contacts =  contactService.getAllContacts();
        return ResponseEntity.ok().body(contacts);
    }

    //create contact
    @PostMapping
    public ResponseEntity<Contact> addContact(@RequestBody Contact contact){
        Contact savedContact = contactService.addContact(contact);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedContact);
    }

    //get contact by id
    @GetMapping("/{id}")
    public ResponseEntity<Contact> getContactById(@PathVariable Long id){
        Contact contact = contactService.getContactById(id);
        return ResponseEntity.ok().body(contact);
    }

    //update contact by id
    @PutMapping("/{id}")
    public ResponseEntity<Contact> updateContactById(@PathVariable Long id,@RequestBody Contact contact){
       Contact updatedcontact = contactService.updateContactById(id,contact);
        return ResponseEntity.ok().body(updatedcontact);
    }
    //delete contact by id
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteContactById(@PathVariable  Long id){
        contactService.deleteContactById(id);
        return ResponseEntity.ok().body("Contact Deleted Successfully");
    }
}
