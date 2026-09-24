package com.contact_manager.controller;

import com.contact_manager.dto.ContactDtoRequest;
import com.contact_manager.dto.ContactDtoResponse;
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
    public ResponseEntity<ApiResponse> addContact(@Valid @RequestBody ContactDtoRequest contactDtoRequest){
        ContactDtoResponse savedContact = contactService.addContact(contactDtoRequest);
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
        List<ContactDtoResponse> contacts =  contactService.getAllContacts();
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
        ContactDtoResponse contactDtoResponse = contactService.getContactById(id);
        ApiResponse response = new ApiResponse(
                true,
                "Contact retrieved successfully",
                contactDtoResponse
        );
        return ResponseEntity.ok().body(response);
    }

    //update contact by id
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateContactById(@PathVariable Long id, @Valid @RequestBody ContactDtoRequest contactDtoRequest){
       ContactDtoResponse updatedContact = contactService.updateContactById(id,contactDtoRequest);
        ApiResponse response = new ApiResponse(
                true,
                "Contact updated successfully",
                updatedContact
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
