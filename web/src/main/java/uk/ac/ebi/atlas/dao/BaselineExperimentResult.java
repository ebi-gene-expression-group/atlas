package uk.ac.ebi.atlas.dao;

public class BaselineExperimentResult {
    private String species;

    private String experimentName;

    private String experimentAccession;


    public BaselineExperimentResult(String experimentAccession, String experimentName, String species) {
        this.experimentAccession = experimentAccession;
        this.experimentName = experimentName;
        this.species = species;
    }

    public String getExperimentAccession() {
        return experimentAccession;
    }

    public void setExperimentAccession(String experimentAccession) {
        this.experimentAccession = experimentAccession;
    }

    public String getExperimentName() {
        return experimentName;
    }

    public void setExperimentName(String experimentName) {
        this.experimentName = experimentName;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

}
