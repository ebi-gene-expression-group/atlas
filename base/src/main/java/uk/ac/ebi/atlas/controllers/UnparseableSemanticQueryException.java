package uk.ac.ebi.atlas.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class UnparseableSemanticQueryException extends RuntimeException {
    public UnparseableSemanticQueryException(String message) {
        super(message);
    }
}
