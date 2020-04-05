package org.k9m.rental.api.exception;

import org.k9m.rental.config.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class CustomerNotFoundException extends ApplicationException {

    public CustomerNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
