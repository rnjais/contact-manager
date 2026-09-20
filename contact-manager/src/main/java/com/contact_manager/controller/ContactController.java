package com.contact_manager.controller;

import com.contact_manager.entity.Contact;
import com.contact_manager.response.ApiResponse;
import com.contact_manager.service.ContactService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/contact")
public class ContactController {

    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    //add contact
    @PostMapping
    public ResponseEntity<ApiResponse> addContact(@Valid @RequestBody  Contact contact){
        Contact savedContact = contactService.addContact(contact);
        ApiResponse response = new ApiResponse(
                true,
                "Contact created successfully",
                savedContact
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    //Retrieve All Contacts
    @GetMapping
    public ResponseEntity<ApiResponse> getAllContacts(){
        List<Contact> contacts =  contactService.getAllContacts();
        ApiResponse response = new ApiResponse(
                true,
                "Contacts retrieved successfully",
                   contacts
                );
        return ResponseEntity.ok().body(response);
    }


    //get contact by id
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getContactById(@PathVariable Long id){
        Contact contact = contactService.getContactById(id);
        ApiResponse response = new ApiResponse(
                true,
                "Contact retrieved successfully",
                contact
        );
        return ResponseEntity.ok().body(response);
    }

    //update contact by id
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateContactById(@PathVariable Long id, @Valid @RequestBody Contact contact){
       Contact updatedcontact = contactService.updateContactById(id,contact);
        ApiResponse response = new ApiResponse(
                true,
                "Contact updated successfully",
                updatedcontact
        );
        return ResponseEntity.ok().body(response);
    }
    //delete contact by id
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteContactById(@PathVariable  Long id){
        contactService.deleteContactById(id);
        ApiResponse response = new ApiResponse(
                true,
                "Contact deleted successfully",
                null
        );
        return ResponseEntity.ok().body(response);
    }
}
