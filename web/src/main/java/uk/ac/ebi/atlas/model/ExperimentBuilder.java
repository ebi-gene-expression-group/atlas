package uk.ac.ebi.atlas.model;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;

import javax.inject.Named;
import java.util.Collection;
import java.util.Set;

import static com.google.common.base.Preconditions.checkState;

@Named
@Scope("prototype")
public class ExperimentBuilder {

    private String specie;
    private String description;
    private Collection<ExperimentRun> experimentRuns;
    private String defaultQueryType;
    private Set<Factor> defaultFilterFactors;

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
        return Collections2.transform(experimentRuns, new Function<ExperimentRun, FactorGroup>() {

            @Override
            public FactorGroup apply(ExperimentRun experimentRun) {
                return experimentRun.getFactorGroup();
            }
        });
    }

    public Experiment create(){
        checkState(StringUtils.isNotBlank(specie), "Please provide a non blank specie");
        checkState(StringUtils.isNotBlank(description), "Please provide a non blank description");
        checkState(StringUtils.isNotBlank(defaultQueryType) , "Please provide a non blank defaultQueryType");
        checkState(CollectionUtils.isNotEmpty(experimentRuns), "Please provide a non empty set of ExperimentRun objects");

        ExperimentalFactors experimentalFactors = buildExperimentalFactors();

        return new Experiment(experimentalFactors, experimentRuns, description, specie);
    }

    ExperimentalFactors buildExperimentalFactors() {
        Collection<FactorGroup> factorGroups = extractFactorGroups();

        ExperimentalFactors experimentalFactors = new ExperimentalFactors(defaultQueryType);

        for (FactorGroup factorGroup : factorGroups) {
            experimentalFactors.addFactorGroup(factorGroup);
        }
        for (Factor defaultFilterFactor : defaultFilterFactors) {
            experimentalFactors.addDefaultFilterFactor(defaultFilterFactor);
        }
        return experimentalFactors;
    }

}