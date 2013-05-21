package uk.ac.ebi.atlas.geneindex;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResultNotFoundException extends RuntimeException {

    public ResultNotFoundException(String message) {
        super(message);
    }

    public ResultNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
