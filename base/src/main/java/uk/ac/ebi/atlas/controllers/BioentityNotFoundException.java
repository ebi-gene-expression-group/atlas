package uk.ac.ebi.atlas.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class BioentityNotFoundException extends RuntimeException {
    public BioentityNotFoundException(String message) {
        super(message);
    }
}
