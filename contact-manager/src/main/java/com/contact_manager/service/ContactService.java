package com.contact_manager.service;

import com.contact_manager.dto.ContactDtoRequest;
import com.contact_manager.dto.ContactDtoResponse;
import com.contact_manager.entity.Contact;
import com.contact_manager.exception.ContactNotFoundException;
import com.contact_manager.exception.InvalidPageAndSizeNumber;
import com.contact_manager.exception.InvalidSortingInput;
import com.contact_manager.mapper.ContactMapper;
import com.contact_manager.repository.ContactRepository;
import com.contact_manager.response.PaginationResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ContactService {

    private final ContactRepository contactRepository;
    private final ContactMapper contactMapper;

    public ContactService(
            ContactRepository contactRepository,
            ContactMapper contactMapper) {

        this.contactRepository = contactRepository;
        this.contactMapper = contactMapper;
    }

    // Add a new contact
    public ContactDtoResponse addContact(
            ContactDtoRequest contactDtoRequest) {

        Contact contact = contactMapper.toEntity(contactDtoRequest);

        contactRepository.save(contact);

        return contactMapper.toDtoResponse(contact);
    }

    // Retrieve contacts with pagination and sorting.
    // Pageable = request information (page, size, sorting)
    // Page = database result containing contacts and pagination information
    public PaginationResponse getAllContacts(
            int page,
            int size,
            String sort,
            String direction) {

        if (page < 0) {
            throw new InvalidPageAndSizeNumber(
                    "Page number must be 0 or greater"
            );
        }

        if (size <= 0) {
            throw new InvalidPageAndSizeNumber(
                    "Page size must be greater than 0"
            );
        }

        Set<String> allowedSortFields = Set.of(
                "id",
                "firstName",
                "lastName",
                "phoneNumber",
                "email",
                "address"
        );

        if (!allowedSortFields.contains(sort)) {
            throw new InvalidSortingInput(
                    "Invalid sort field: " + sort
            );
        }

        if (!direction.equalsIgnoreCase("asc")
                && !direction.equalsIgnoreCase("desc")) {

            throw new InvalidSortingInput(
                    "Invalid sort direction: " + direction
            );
        }

        Sort.Direction sortDirection =
                Sort.Direction.fromString(direction);

        Pageable pageable =
                PageRequest.of(
                        page,
                        size,
                        Sort.by(sortDirection, sort)
                );

        Page<ContactDtoResponse> contacts =
                contactRepository.findAll(pageable)
                        .map(contactMapper::toDtoResponse);

        return new PaginationResponse(
                contacts.getContent(),
                contacts.getNumber(),
                contacts.getSize(),
                contacts.getTotalElements(),
                contacts.getTotalPages(),
                contacts.isFirst(),
                contacts.isLast()
        );
    }

    // Get a contact by ID
    public ContactDtoResponse getContactById(Long id) {

        Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> new ContactNotFoundException(
                        "Contact not found with id " + id
                ));

        return contactMapper.toDtoResponse(contact);
    }

    // Update a contact by ID
    public ContactDtoResponse updateContactById(
            Long id,
            ContactDtoRequest contactDtoRequest) {

        Contact existingContact = contactRepository.findById(id)
                .orElseThrow(() -> new ContactNotFoundException(
                        "Contact not found with id " + id
                ));

        existingContact.setFirstName(
                contactDtoRequest.getFirstName()
        );

        existingContact.setLastName(
                contactDtoRequest.getLastName()
        );

        existingContact.setEmail(
                contactDtoRequest.getEmail()
        );

        existingContact.setPhoneNumber(
                contactDtoRequest.getPhoneNumber()
        );

        existingContact.setAddress(
                contactDtoRequest.getAddress()
        );

        contactRepository.save(existingContact);

        return contactMapper.toDtoResponse(existingContact);
    }

    // Delete a contact by ID
    public void deleteContactById(Long id) {

        Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> new ContactNotFoundException(
                        "Contact not found with id " + id
                ));

        contactRepository.delete(contact);
    }

    // Search contacts by first name
    public List<ContactDtoResponse> searchByFirstName(
            String firstName) {

        List<ContactDtoResponse> contacts =
                contactRepository
                        .findByFirstNameContainingIgnoreCase(firstName)
                        .stream()
                        .map(contactMapper::toDtoResponse)
                        .toList();

        if (contacts.isEmpty()) {
            throw new ContactNotFoundException(
                    "No contacts found with first name " + firstName
            );
        }

        return contacts;
    }

    // Search contacts by last name
    public List<ContactDtoResponse> searchByLastName(
            String lastName) {

        List<ContactDtoResponse> contacts =
                contactRepository
                        .findByLastNameContainingIgnoreCase(lastName)
                        .stream()
                        .map(contactMapper::toDtoResponse)
                        .toList();

        if (contacts.isEmpty()) {
            throw new ContactNotFoundException(
                    "No contacts found with last name " + lastName
            );
        }

        return contacts;
    }

    // Search a contact by email
    public ContactDtoResponse searchByEmail(String email) {

        Contact contact = contactRepository.findByEmail(email)
                .orElseThrow(() -> new ContactNotFoundException(
                        "Contact not found with email " + email
                ));

        return contactMapper.toDtoResponse(contact);
    }

    // Search a contact by phone number
    public ContactDtoResponse searchByPhone(String phoneNumber) {

        Contact contact = contactRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new ContactNotFoundException(
                        "Contact not found with phone number "
                                + phoneNumber
                ));

        return contactMapper.toDtoResponse(contact);
    }
}
