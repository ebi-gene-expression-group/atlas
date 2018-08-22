package uk.ac.ebi.atlas.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//TODO Make this a subclass of MissingResourceException to include fields about the resource class and key
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public final class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(Exception exception) {
        super(exception);
    }
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
