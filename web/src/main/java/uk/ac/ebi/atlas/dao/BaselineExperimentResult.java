package uk.ac.ebi.atlas.dao;

import com.google.common.collect.Sets;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.baseline.Factor;

import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class BaselineExperimentResult {
    private String species;

    private String experimentName;

    private String experimentAccession;

    private SortedSet<Factor> defaultFactorsForSpecificAssayGroupsWithCondition = new TreeSet<>();

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

    // used in baselineCounts-widget.jsp
    public SortedSet<String> getDefaultFactorValuesForSpecificAssayGroupsWithCondition() {
        return Factor.getValues(defaultFactorsForSpecificAssayGroupsWithCondition);
    }

    public void setAssayGroupsWithCondition(Set<String> assayGroupsWithCondition, BaselineExperiment experiment) {
        setDefaultFactorValuesForSpecificAssayGroupsWithCondition(assayGroupsWithCondition, experiment);
    }

    private void setDefaultFactorValuesForSpecificAssayGroupsWithCondition(Set<String> assayGroupsWithCondition, BaselineExperiment experiment) {
        defaultFactorsForSpecificAssayGroupsWithCondition = extractDefaultFactorsForSpecificAssayGroupsWithCondition(assayGroupsWithCondition, experiment);
    }

    // this is the default factor type across all specific assay groups with the condition
    private SortedSet<Factor> extractDefaultFactorsForSpecificAssayGroupsWithCondition(Set<String> assayGroupsWithCondition, BaselineExperiment experiment) {
        return experiment.getAssayGroupFactors(getSpecificAssayGroupsWithCondition(assayGroupsWithCondition, experiment), experiment.getDefaultQueryFactorType());
    }

    // if all assay groups have the condition, returns the empty set, otherwise returns
    // only those assay groups with the condition
    private Set<String> getSpecificAssayGroupsWithCondition(Set<String> assayGroupsWithCondition, BaselineExperiment experiment) {
        Set<String> allAssayGroupIds = experiment.getAssayGroups().getAssayGroupIds();
        Set<String> intersect = Sets.intersection(assayGroupsWithCondition, allAssayGroupIds);
        return (intersect.size() == allAssayGroupIds.size()) ? new HashSet<String>(): assayGroupsWithCondition;
    }

    public SortedSet<Factor> getDefaultFactorsForSpecificAssayGroupsWithCondition() {
        return defaultFactorsForSpecificAssayGroupsWithCondition;
    }
}