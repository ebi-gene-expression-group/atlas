package uk.ac.ebi.atlas.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.OK)
public class NoStatisticalSignificanceException extends RuntimeException {
    public NoStatisticalSignificanceException(String message) {
        super(message);
    }
}
