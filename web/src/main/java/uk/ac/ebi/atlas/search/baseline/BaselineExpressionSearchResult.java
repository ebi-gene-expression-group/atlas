package uk.ac.ebi.atlas.search.baseline;

import com.google.common.collect.Sets;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.model.baseline.FactorGroup;

import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class BaselineExpressionSearchResult implements Comparable<BaselineExpressionSearchResult> {
    private String species;

    private String experimentName;

    private String experimentAccession;

    private SortedSet<Factor> defaultFactorsForSpecificAssayGroupsWithCondition = new TreeSet<>();
    private FactorGroup filterFactors;

    public BaselineExpressionSearchResult(String experimentAccession, String experimentName, String species) {
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

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public SortedSet<String> getDefaultFactorValuesForSpecificAssayGroupsWithCondition() {
        return Factor.getValues(defaultFactorsForSpecificAssayGroupsWithCondition);
    }

    public SortedSet<Factor> getDefaultFactorsForSpecificAssayGroupsWithCondition() {
        return defaultFactorsForSpecificAssayGroupsWithCondition;
    }

    public void setAssayGroupsWithCondition(Set<String> assayGroupIdsWithCondition, BaselineExperiment experiment) {
        setDefaultFactorValuesForSpecificAssayGroupsWithCondition(assayGroupIdsWithCondition, experiment);
    }

    private void setDefaultFactorValuesForSpecificAssayGroupsWithCondition(Set<String> assayGroupIdsWithCondition, BaselineExperiment experiment) {
        defaultFactorsForSpecificAssayGroupsWithCondition = defaultFactorsForSpecificAssayGroupIdsWithCondition(assayGroupIdsWithCondition, experiment);
    }

    // this is the default factor type across all specific assay groups with the condition
    private SortedSet<Factor> defaultFactorsForSpecificAssayGroupIdsWithCondition(Set<String> assayGroupIdsWithCondition, BaselineExperiment experiment) {
        return experiment.getAssayGroupFactors(specificAssayGroupIdsWithCondition(assayGroupIdsWithCondition, experiment), experiment.getExperimentalFactors().getDefaultQueryFactorType());
    }

    // If all assay groups have expression, then it is not specific and so returns the empty set. 
    // Otherwise returns only those assay groups with the expression
    private Set<String> specificAssayGroupIdsWithCondition(Set<String> assayGroupIdsWithCondition, BaselineExperiment experiment) {
        Set<String> allAssayGroupIds = experiment.getAssayGroups().getAssayGroupIds();
        Set<String> intersect = Sets.intersection(assayGroupIdsWithCondition, allAssayGroupIds);
        return (intersect.size() == allAssayGroupIds.size()) ? new HashSet<String>(): assayGroupIdsWithCondition;
    }

    public void setFilterFactors(FactorGroup filterFactors) {
        this.filterFactors = filterFactors;
    }

    public FactorGroup getFilterFactors() {
        return filterFactors;
    }

    @Override
    public int compareTo(BaselineExpressionSearchResult o) {
        int c = this.getExperimentName().compareTo(o.getExperimentName());
        return (c != 0) ? c : this.filterFactors.compareTo(o.getFilterFactors());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BaselineExpressionSearchResult that = (BaselineExpressionSearchResult) o;

        return experimentName.equals(that.experimentName) && filterFactors.equals(that.filterFactors);
    }

    @Override
    public int hashCode() {
        int result = experimentName.hashCode();
        result = 31 * result + filterFactors.hashCode();
        return result;
    }
}