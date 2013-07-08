package uk.ac.ebi.atlas.expdesign;

public class ExperimentDesignWritingException extends Exception {
    public ExperimentDesignWritingException(String message) {
        super(message);
    }

    public ExperimentDesignWritingException(String message, Throwable cause) {
        super(message, cause);
    }
}
