package uk.ac.ebi.atlas.acceptance.selenium.pages;

public class BaselineBioEntitiesSearchResult {

    private final String href;

    private String species;

    private String experimentName;

    private String experimentAccession;

    private String assayGroupOrContrast;

    private int count;

    public BaselineBioEntitiesSearchResult(String experimentName, String species, String experimentAccession, int count, String href){
        this.experimentAccession = experimentAccession;
        this.experimentName = experimentName;
        this.species = species;
        this.href = href;
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

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getAssayGroupOrContrast() {
        return assayGroupOrContrast;
    }

    public String getHref() {
        return href;
    }

}
