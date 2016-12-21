
package uk.ac.ebi.atlas.utils;

import com.google.common.collect.Sets;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;

import java.util.Set;
import java.util.SortedSet;

public class ExperimentInfo implements Comparable<ExperimentInfo> {

    private ExperimentType experimentType;
    private String experimentAccession;
    private String experimentDescription;
    private String lastUpdate;
    private int numberOfAssays;
    private int numberOfContrasts;
    private String species;
    private String kingdom;
    private SortedSet<String> experimentalFactors = Sets.newTreeSet();
    private SortedSet<String> arrayDesigns = Sets.newTreeSet();
    private SortedSet<String> arrayDesignNames = Sets.newTreeSet();

    public ExperimentType getExperimentType() {
        return experimentType;
    }

    public void setExperimentType(ExperimentType experimentType) {
        this.experimentType = experimentType;
    }

    public String getExperimentAccession() {
        return experimentAccession;
    }

    public void setExperimentAccession(String experimentAccession) {
        this.experimentAccession = experimentAccession;
    }

    public String getExperimentDescription() {
        return experimentDescription;
    }

    public void setExperimentDescription(String experimentDescription) {
        this.experimentDescription = experimentDescription;
    }

    public int getNumberOfAssays() {
        return numberOfAssays;
    }

    public void setNumberOfAssays(int numberOfAssays) {
        this.numberOfAssays = numberOfAssays;
    }

    public int getNumberOfContrasts() {
        return numberOfContrasts;
    }

    public void setNumberOfContrasts(int numberOfContrasts) {
        this.numberOfContrasts = numberOfContrasts;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getKingdom() {
        return kingdom;
    }

    public void setKingdom(String kingdom) {
        this.kingdom = kingdom;
    }

    public SortedSet<String> getExperimentalFactors() {
        return experimentalFactors;
    }

    public void setExperimentalFactors(Set<String> experimentalFactors) {
        this.experimentalFactors = Sets.newTreeSet(experimentalFactors);
    }

    public SortedSet<String> getArrayDesigns() {
        return arrayDesigns;
    }

    public void setArrayDesigns(Set<String> arrayDesigns) {
        this.arrayDesigns = Sets.newTreeSet(arrayDesigns);
    }

    public SortedSet<String> getArrayDesignNames() {
        return arrayDesignNames;
    }

    public void setArrayDesignNames(SortedSet<String> arrayDesignNames) {
        this.arrayDesignNames = arrayDesignNames;
    }

    @Override
    public int compareTo(ExperimentInfo o) {
        return this.experimentAccession.compareTo(o.experimentAccession);
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}