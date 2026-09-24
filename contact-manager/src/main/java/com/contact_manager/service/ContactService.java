package com.contact_manager.service;

import com.contact_manager.dto.ContactDtoRequest;
import com.contact_manager.dto.ContactDtoResponse;
import com.contact_manager.entity.Contact;
import com.contact_manager.exception.ContactNotFoundException;
import com.contact_manager.mapper.ContactMapper;
import com.contact_manager.repository.ContactRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ContactService {

    private final ContactRepository contactRepository;
    private final ContactMapper contactMapper;

    public ContactService(ContactRepository contactRepository, ContactMapper contactMapper) {
        this.contactRepository = contactRepository;
        this.contactMapper = contactMapper;
    }

    // add contact
    public ContactDtoResponse addContact(ContactDtoRequest contactDtoRequest) {
        Contact contact = contactMapper.toEntity(contactDtoRequest);
        contactRepository.save(contact);
        return contactMapper.toDtoResponse(contact);

    }

    //Retrieve all contact
    public List<ContactDtoResponse> getAllContacts() {
        return contactRepository.findAll()
                .stream()
                .map(contactMapper::toDtoResponse)
                .toList();
    }

    //get contact by id
    public ContactDtoResponse getContactById(Long id) {
        Contact contact = contactRepository.findById(id).orElseThrow(() -> new ContactNotFoundException("Contact not found with id " + id));
        return contactMapper.toDtoResponse(contact);

    }

    //    update contact by id
    public ContactDtoResponse updateContactById(Long id, ContactDtoRequest contactDtoRequest) {
        Contact existingContact = contactRepository.findById(id).orElseThrow(() -> new ContactNotFoundException("Contact not found with id " + id));

        existingContact.setFirstName(contactDtoRequest.getFirstName());
        existingContact.setLastName(contactDtoRequest.getLastName());
        existingContact.setEmail(contactDtoRequest.getEmail());
        existingContact.setPhoneNumber(contactDtoRequest.getPhoneNumber());
        existingContact.setAddress(contactDtoRequest.getAddress());
        contactRepository.save(existingContact);

        return contactMapper.toDtoResponse(existingContact);
    }

    //    delete contact by id
    public void deleteContactById(Long id) {
        Contact contact = contactRepository.findById(id).orElseThrow(() -> new ContactNotFoundException("Contact not found with id " + id));
        contactRepository.delete(contact);
    }
}


