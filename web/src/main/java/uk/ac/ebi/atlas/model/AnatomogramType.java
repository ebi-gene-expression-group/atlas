package uk.ac.ebi.atlas.model;

/**
 * Created by barrera on 08/07/2015.
 */
public enum AnatomogramType {

    HOMO_SAPIENS_MALE("homo_sapiens_male"),
    HOMO_SAPIENS_FEMALE("homo_sapiens_female"),
    HOMO_SAPIENS_BRAIN("homo_sapiens_brain");

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
