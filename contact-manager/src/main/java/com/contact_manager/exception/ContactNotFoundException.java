package com.contact_manager.exception;

public class ContactNotFoundException extends RuntimeException{
    public ContactNotFoundException (String message){
        super(message);
    }
}
