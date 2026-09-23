package com.contact_manager.mapper;

import com.contact_manager.dto.ContactDTO;
import com.contact_manager.entity.Contact;
import org.springframework.stereotype.Component;

@Component
public class ContactMapper {

    public Contact toEntity(ContactDTO contactDTO) {
        Contact contact = new Contact();
        contact.setId(contactDTO.getId());
        contact.setFirstName(contactDTO.getFirstName());
        contact.setLastName(contactDTO.getLastName());
        contact.setEmail(contactDTO.getEmail());
        contact.setPhoneNumber(contactDTO.getPhoneNumber());
        contact.setAddress(contactDTO.getAddress());
        return contact;

    }

    public ContactDTO toDTO(Contact contact) {
        ContactDTO contactDTO = new ContactDTO();
        contactDTO.setId(contact.getId());
        contactDTO.setFirstName(contact.getFirstName());
        contactDTO.setLastName(contact.getLastName());
        contactDTO.setEmail(contact.getEmail());
        contactDTO.setPhoneNumber(contact.getPhoneNumber());
        contactDTO.setAddress(contact.getAddress());
        return contactDTO;
    }
}
