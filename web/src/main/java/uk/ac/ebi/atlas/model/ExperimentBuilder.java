package uk.ac.ebi.atlas.model;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import static com.google.common.base.Preconditions.checkState;

@Named
@Scope("prototype")
public class ExperimentBuilder{

    private String specie;
    private String description;
    private Collection<ExperimentRun> experimentRuns;
    private String defaultQueryType;
    private Set<Factor> defaultFilterFactors;
    private ExperimentalFactors experimentalFactors;

    @Inject
    ExperimentBuilder(ExperimentalFactors experimentalFactors){
        this.experimentalFactors = experimentalFactors;
    }

    public ExperimentBuilder forSpecie(String specie){
        this.specie = specie;
        return this;
    }

    public ExperimentBuilder withDescription(String description){
        this.description = description;
        return this;
    }

    public ExperimentBuilder withExperimentRuns(Collection<ExperimentRun> experimentRuns){
        this.experimentRuns = experimentRuns;
        return this;
    }

    public ExperimentBuilder withDefaultQueryType(String defaultQueryType){
        this.defaultQueryType = defaultQueryType;
        return this;
    }

    public ExperimentBuilder withDefaultFilterFactors(Set<Factor> defaultFilterFactors){
        this.defaultFilterFactors = defaultFilterFactors;
        return this;
    }

    Collection<FactorGroup> extractFactorGroups() {
        Collection<FactorGroup> factorGroups = new ArrayList();
        for (ExperimentRun experimentRun: experimentRuns){
            factorGroups.add(experimentRun.getFactorGroup());
        }
       return factorGroups;
    }

    public Experiment create(){
        checkState(StringUtils.isNotBlank(specie), "Please provide a non blank specie");
        checkState(StringUtils.isNotBlank(description), "Please provide a non blank description");
        checkState(StringUtils.isNotBlank(defaultQueryType) , "Please provide a non blank defaultQueryType");
        checkState(CollectionUtils.isNotEmpty(experimentRuns), "Please provide a non empty set of ExperimentRun objects");

        buildExperimentalFactors();

        for (Factor defaultFilterFactor: defaultFilterFactors){
            String factorName = experimentalFactors.getFactorName(defaultFilterFactor.getType());
            defaultFilterFactor.setName(factorName);
        }

        return new Experiment(experimentalFactors, experimentRuns, description, specie, defaultQueryType, defaultFilterFactors);
    }

    ExperimentalFactors buildExperimentalFactors() {
        Collection<FactorGroup> factorGroups = extractFactorGroups();

        for (FactorGroup factorGroup : factorGroups) {
            experimentalFactors.addFactorGroup(factorGroup);
        }
        return experimentalFactors;
    }

}