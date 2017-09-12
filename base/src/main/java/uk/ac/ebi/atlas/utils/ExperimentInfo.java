
package uk.ac.ebi.atlas.utils;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;

import java.util.List;
import java.util.Set;
import java.util.SortedSet;

/*
I'm a useful class, just old. I show up as rows on Experiments page, and for latest experiments I am rendered
server-side through templates.
For the sake of latest experiments I need to stay a bean, with method names as explained in
 http://stackoverflow.com/a/8577719
Also since I am a bean, that setting properties is convenient and constructing me in Experiment is convenient.
I could have a toJson method, and then there will be no need for reflection-based serialization that we're currently
(and confusingly) doing.
lastUpdate is a String that is later parsed, which, if you implement a toJson method, you could improve.
 */
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
    private List<String> arrayDesigns = ImmutableList.of();
    private List<String> arrayDesignNames = ImmutableList.of();

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

    public List<String> getArrayDesigns() {
        return arrayDesigns;
    }

    public void setArrayDesigns(List<String> arrayDesigns) {
        this.arrayDesigns = arrayDesigns;
    }

    public List<String> getArrayDesignNames() {
        return arrayDesignNames;
    }

    public void setArrayDesignNames(List<String> arrayDesignNames) {
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