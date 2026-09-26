package com.contact_manager.service;

import com.contact_manager.dto.ContactDtoRequest;
import com.contact_manager.dto.ContactDtoResponse;
import com.contact_manager.entity.Contact;
import com.contact_manager.exception.ContactNotFoundException;
import com.contact_manager.mapper.ContactMapper;
import com.contact_manager.repository.ContactRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public Page<ContactDtoResponse> getAllContacts(
            int page,
            int size,
            String sort,
            String direction) {

        Sort.Direction sortDirection =
                Sort.Direction.fromString(direction);

        Pageable pageable =
                PageRequest.of(page, size, Sort.by(sortDirection, sort));

        return contactRepository.findAll(pageable)
                .map(contactMapper::toDtoResponse);
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


    public List<ContactDtoResponse> searchByFirstName(String firstName){
        List<ContactDtoResponse> contacts = contactRepository.findByFirstNameContainingIgnoreCase(firstName)
                .stream()
                .map(contactMapper::toDtoResponse)
                .toList();
        if(contacts.isEmpty()){
            throw new ContactNotFoundException("No contacts found with first name " + firstName);
        }
    return contacts;
    }

//    search by last name
    public List<ContactDtoResponse> searchByLastName(String lastName){
        List<ContactDtoResponse> contacts = contactRepository.findByLastNameContainingIgnoreCase(lastName)
                .stream()
                .map(contactMapper::toDtoResponse)
                .toList();
        if(contacts.isEmpty()){
            throw new ContactNotFoundException("No contacts found with last name " + lastName);
        }
    return contacts;
    }

    //find by email
    public ContactDtoResponse searchByEmail(String email){

        Contact contact = contactRepository.findByEmail(email).orElseThrow(()->
                new ContactNotFoundException("Contact not found with email "+ email));
        return contactMapper.toDtoResponse(contact);
    }
//    find by phone number
    public ContactDtoResponse searchByPhone(String phoneNumber){
        Contact contact = contactRepository.findByPhoneNumber(phoneNumber).orElseThrow(()-> new ContactNotFoundException("contact not found with phone number " +phoneNumber));
        return contactMapper.toDtoResponse(contact);
    }
 }


//                          Pageable = REQUEST
//                                   ↓
//                      "Give me page 0, 5 records"
//
//                              Page = RESPONSE
//                                   ↓
//               "Here are those 5 records + information about all pages"
