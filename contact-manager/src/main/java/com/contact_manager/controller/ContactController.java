
package com.contact_manager.controller;

import com.contact_manager.dto.ContactDtoRequest;
import com.contact_manager.dto.ContactDtoResponse;
import com.contact_manager.response.ApiResponse;
import com.contact_manager.response.PaginationResponse;
import com.contact_manager.service.ContactService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/contact")
public class ContactController {

    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    // Create a new contact
    @PostMapping
    public ResponseEntity<ApiResponse> addContact(
            @Valid @RequestBody ContactDtoRequest contactDtoRequest) {

        ContactDtoResponse savedContact =
                contactService.addContact(contactDtoRequest);

        ApiResponse response = new ApiResponse(
                true,
                "Contact created successfully",
                savedContact
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    // Retrieve all contacts with pagination and sorting
    @GetMapping
    public ResponseEntity<ApiResponse> getAllContacts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "firstName") String sort,
            @RequestParam(defaultValue = "asc") String direction) {

        PaginationResponse contacts =
                contactService.getAllContacts(
                        page,
                        size,
                        sort,
                        direction
                );

        ApiResponse response = new ApiResponse(
                true,
                "Contacts retrieved successfully",
                contacts
        );

        return ResponseEntity.ok(response);
    }

    // Search contacts by first name
    @GetMapping("/search/firstName")
    public ResponseEntity<ApiResponse> searchByFirstName(
            @RequestParam String firstName) {

        List<ContactDtoResponse> contacts =
                contactService.searchByFirstName(firstName);

        ApiResponse response = new ApiResponse(
                true,
                "Contact found successfully",
                contacts
        );

        return ResponseEntity.ok(response);
    }

    // Search contacts by last name
    @GetMapping("/search/lastName")
    public ResponseEntity<ApiResponse> searchByLastName(
            @RequestParam String lastName) {

        List<ContactDtoResponse> contacts =
                contactService.searchByLastName(lastName);

        ApiResponse response = new ApiResponse(
                true,
                "Contact found successfully",
                contacts
        );

        return ResponseEntity.ok(response);
    }

    // Search contact by email
    @GetMapping("/search/email")
    public ResponseEntity<ApiResponse> searchByEmail(
            @RequestParam String email) {

        ContactDtoResponse contact =
                contactService.searchByEmail(email);

        ApiResponse response = new ApiResponse(
                true,
                "Contact found successfully",
                contact
        );

        return ResponseEntity.ok(response);
    }

    // Search contact by phone number
    @GetMapping("/search/phoneNumber")
    public ResponseEntity<ApiResponse> searchByPhone(
            @RequestParam String phoneNumber) {

        ContactDtoResponse contact =
                contactService.searchByPhone(phoneNumber);

        ApiResponse response = new ApiResponse(
                true,
                "Contact found successfully",
                contact
        );

        return ResponseEntity.ok(response);
    }

    // Retrieve a contact by ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getContactById(
            @PathVariable Long id) {

        ContactDtoResponse contactDtoResponse =
                contactService.getContactById(id);

        ApiResponse response = new ApiResponse(
                true,
                "Contact retrieved successfully",
                contactDtoResponse
        );

        return ResponseEntity.ok(response);
    }

    // Update a contact by ID
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateContactById(
            @PathVariable Long id,
            @Valid @RequestBody ContactDtoRequest contactDtoRequest) {

        ContactDtoResponse updatedContact =
                contactService.updateContactById(
                        id,
                        contactDtoRequest
                );

        ApiResponse response = new ApiResponse(
                true,
                "Contact updated successfully",
                updatedContact
        );

        return ResponseEntity.ok(response);
    }

    // Delete a contact by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteContactById(
            @PathVariable Long id) {

        contactService.deleteContactById(id);

        ApiResponse response = new ApiResponse(
                true,
                "Contact deleted successfully",
                null
        );

        return ResponseEntity.ok(response);
    }
}
