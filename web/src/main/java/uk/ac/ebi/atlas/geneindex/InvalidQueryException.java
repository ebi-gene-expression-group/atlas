package uk.ac.ebi.atlas.geneindex;

public class InvalidQueryException extends RuntimeException {

    public InvalidQueryException(String message, Throwable cause) {
        super(message, cause);
    }
}
