package com.contact_manager.exception;

public class InvalidPageAndSizeNumber extends IllegalArgumentException{
    public InvalidPageAndSizeNumber(String message){
        super(message);
    }
}
