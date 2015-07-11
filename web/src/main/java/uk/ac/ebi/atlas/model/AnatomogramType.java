package uk.ac.ebi.atlas.model;

/**
 * Created by barrera on 08/07/2015.
 */
public enum AnatomogramType {

    MALE("male"),
    FEMALE("female"),
    BRAIN("brain");

    private AnatomogramType anatomogramType;
    private String description;


    private AnatomogramType(String description) {
        this.description = description;
    }

    private AnatomogramType(AnatomogramType anatomogramType, String description) {
        this.anatomogramType = anatomogramType;
        this.description = description;
    }

    public AnatomogramType getAnatomogramType() {
        return anatomogramType;
    }

    public String getDescription() {
        return description;
    }

}
