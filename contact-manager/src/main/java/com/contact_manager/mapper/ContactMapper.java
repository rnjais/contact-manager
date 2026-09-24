package com.contact_manager.mapper;

import com.contact_manager.dto.ContactDtoRequest;
import com.contact_manager.dto.ContactDtoResponse;
import com.contact_manager.entity.Contact;
import org.springframework.stereotype.Component;

@Component
public class ContactMapper {

    // Request DTO → Entity
    public Contact toEntity(ContactDtoRequest contactDtoRequest) {
        Contact contact = new Contact();
        contact.setFirstName(contactDtoRequest.getFirstName());
        contact.setLastName(contactDtoRequest.getLastName());
        contact.setEmail(contactDtoRequest.getEmail());
        contact.setPhoneNumber(contactDtoRequest.getPhoneNumber());
        contact.setAddress(contactDtoRequest.getAddress());
        return contact;

    }

    // Entity → Response DTO
    public ContactDtoResponse toDtoResponse(Contact contact) {
        return new ContactDtoResponse(
                contact.getId(),
                contact.getFirstName(),
                contact.getLastName(),
                contact.getPhoneNumber(),
                contact.getEmail(),
                contact.getAddress()
        );
    }
}
