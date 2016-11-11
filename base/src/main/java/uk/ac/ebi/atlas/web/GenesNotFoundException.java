package uk.ac.ebi.atlas.web;

public class GenesNotFoundException extends Exception {
    public GenesNotFoundException() {
        super();
    }
    public GenesNotFoundException(String message){
        super(message);
    }
}