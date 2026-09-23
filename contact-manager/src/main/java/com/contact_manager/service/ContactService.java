package com.contact_manager.service;

import com.contact_manager.dto.ContactDTO;
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
    public ContactDTO addContact(ContactDTO contactDTO){
        Contact contact = contactMapper.toEntity(contactDTO);
                contactRepository.save(contact);

        return contactMapper.toDTO(contact);

    }

    //Retrieve all contact
    public List<ContactDTO> getAllContacts() {
        return contactRepository.findAll()
                .stream()
                .map(contactMapper::toDTO)
                .toList();
    }

    //get contact by id
    public ContactDTO getContactById(Long id) {
          Contact contact = contactRepository.findById(id).orElseThrow(() -> new ContactNotFoundException("Contact not found with id "+id));
          return contactMapper.toDTO(contact);

    }

//    update contact by id
    public ContactDTO updateContactById(Long id, ContactDTO contactDTO) {
        Contact existingContact = contactRepository.findById(id).orElseThrow(() -> new ContactNotFoundException("Contact not found with id " +id));

        existingContact.setFirstName(contactDTO.getFirstName());
        existingContact.setLastName(contactDTO.getLastName());
        existingContact.setEmail(contactDTO.getEmail());
        existingContact.setPhoneNumber(contactDTO.getPhoneNumber());
        existingContact.setAddress((contactDTO.getAddress()));
        contactRepository.save(existingContact);

        return contactMapper.toDTO(existingContact);
    }

//    delete contact by id
    public void deleteContactById(Long id) {
        Contact contact = contactRepository.findById(id).orElseThrow(() -> new ContactNotFoundException("Contact not found with id " + id));
        contactRepository.delete(contact);
    }
}


