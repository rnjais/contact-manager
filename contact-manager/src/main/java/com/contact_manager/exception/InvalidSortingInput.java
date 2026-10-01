package com.contact_manager.exception;

import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.resource.NoResourceFoundException;

public class InvalidSortingInput extends InvalidPageAndSizeNumber {
    public InvalidSortingInput(String message) {
        super(message);
    }
}
