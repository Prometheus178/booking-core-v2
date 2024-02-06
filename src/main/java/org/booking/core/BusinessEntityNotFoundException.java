package org.booking.core;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BusinessEntityNotFoundException extends RuntimeException {

    public BusinessEntityNotFoundException(String entityName, Long entityId) {
        super(String.format("%s with id '%s' not found", entityName, entityId));
    }
}
