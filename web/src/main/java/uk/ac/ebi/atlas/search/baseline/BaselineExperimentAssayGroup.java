package uk.ac.ebi.atlas.search.baseline;

import com.google.common.collect.Sets;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.model.baseline.FactorGroup;
import uk.ac.ebi.atlas.web.FilterFactorsConverter;

import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class BaselineExperimentAssayGroup implements Comparable<BaselineExperimentAssayGroup> {
    private String species;

    private String experimentName;

    private String experimentAccession;

    private SortedSet<Factor> defaultFactorsForSpecificAssayGroupsWithCondition = new TreeSet<>();

    private FactorGroup filterFactors;

    private String defaultQueryFactorType;

    private FilterFactorsConverter filterFactorsConverter = new FilterFactorsConverter();

    private boolean tissueExperiment;

    public BaselineExperimentAssayGroup(String experimentAccession, String experimentName, String species, String defaultQueryFactorType, boolean tissueExperiment) {
        this.experimentAccession = experimentAccession;
        this.experimentName = experimentName;
        this.species = species;
        this.defaultQueryFactorType = defaultQueryFactorType;
        this.tissueExperiment = tissueExperiment;
    }

    public boolean isTissueExperiment() {
        return tissueExperiment;
    }

    public String getExperimentAccession() {
        return experimentAccession;
    }

    public String getExperimentName() {
        return experimentName;
    }

    public String getSpecies() {
        return species;
    }

    public String getDefaultQueryFactorType() {
        return defaultQueryFactorType;
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
    public int compareTo(BaselineExperimentAssayGroup o) {
        return this.toString().compareTo(o.toString());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BaselineExperimentAssayGroup that = (BaselineExperimentAssayGroup) o;

        return experimentName.equals(that.experimentName) && filterFactors.equals(that.filterFactors);
    }

    @Override
    public int hashCode() {
        int result = experimentName.hashCode();
        result = 31 * result + filterFactors.hashCode();
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(species).append(" - ").append(experimentName);
        if (!filterFactors.isEmpty() && !filterFactors.containsOnlyOrganism()) {
            sb.append(" - ").append(filterFactorsConverter.prettyPrint(filterFactors));
        }
        return sb.toString();
    }
}