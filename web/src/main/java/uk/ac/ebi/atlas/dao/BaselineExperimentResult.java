package uk.ac.ebi.atlas.dao;

import com.google.common.collect.Sets;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.baseline.Factor;

import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;

public class BaselineExperimentResult {
    private String species;

    private String experimentName;

    private String experimentAccession;

    private BaselineExperiment experiment;
    private Set<String> assayGroupsWithCondition;

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

    // this is the default factor type across all specific assay groups with the condition
    public SortedSet<Factor> getDefaultFactorsForSpecificAssayGroupsWithCondition() {
        return experiment.getAssayGroupFactors(getSpecificAssayGroupsWithCondition(), experiment.getDefaultQueryFactorType());
    }

    public SortedSet<String> getDefaultFactorValuesForSpecificAssayGroupsWithCondition() {
        return Factor.getValues(getDefaultFactorsForSpecificAssayGroupsWithCondition());
    }

    public void setExperiment(BaselineExperiment experiment) {
        this.experiment = experiment;
    }

    public void setAssayGroupsWithCondition(Set<String> assayGroupsWithCondition) {
        this.assayGroupsWithCondition = assayGroupsWithCondition;
    }

    public Set<String> getAssayGroupsWithCondition() {
        return assayGroupsWithCondition;
    }

    // if all assay groups have the condition, returns the empty set, otherwise returns
    // only those assay groups with the condition
    public Set<String> getSpecificAssayGroupsWithCondition() {
        Set<String> allAssayGroupIds = experiment.getAssayGroups().getAssayGroupIds();
        Set<String> intersect = Sets.intersection(assayGroupsWithCondition, allAssayGroupIds);
        return (intersect.size() == allAssayGroupIds.size()) ? new HashSet<String>(): assayGroupsWithCondition;
    }

}
