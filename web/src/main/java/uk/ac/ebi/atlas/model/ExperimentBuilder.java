/*
 * Copyright 2008-2013 Microarray Informatics Team, EMBL-European Bioinformatics Institute
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 * For further details of the Gene Expression Atlas project, including source code,
 * downloads and documentation, please see:
 *
 * http://gxa.github.com/gxa
 */

package uk.ac.ebi.atlas.model;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import static com.google.common.base.Preconditions.checkState;

@Named
@Scope("prototype")
public class ExperimentBuilder {

    private Set<String> species;
    private String description;
    private String displayName;
    private Collection<ExperimentRun> experimentRuns;
    private String defaultQueryType;
    private boolean hasExtraInfoFile;
    private Set<Factor> defaultFilterFactors;
    private Set<String> menuFilterFactorTypes;
    private ExperimentalFactorsBuilder experimentalFactorsBuilder;
    private Map<String, String> factorNamesByType;
    private String experimentAccession;

    @Inject
    ExperimentBuilder(ExperimentalFactorsBuilder experimentalFactorsBuilder) {
        this.experimentalFactorsBuilder = experimentalFactorsBuilder;
    }

    public ExperimentBuilder forSpecies(Set<String> species) {
        this.species = species;
        return this;
    }

    public ExperimentBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public ExperimentBuilder withExperimentRuns(Collection<ExperimentRun> experimentRuns) {
        this.experimentRuns = experimentRuns;
        return this;
    }

    public ExperimentBuilder withDefaultQueryType(String defaultQueryType) {
        this.defaultQueryType = defaultQueryType;
        return this;
    }

    public ExperimentBuilder withDefaultFilterFactors(Set<Factor> defaultFilterFactors) {
        this.defaultFilterFactors = defaultFilterFactors;
        return this;
    }

    public ExperimentBuilder withExtraInfo(boolean hasExtraInfoFile) {
        this.hasExtraInfoFile = hasExtraInfoFile;
        return this;
    }

    public ExperimentBuilder withMenuFilterFactorTypes(Set<String> menuFilterFactorTypes) {
        this.menuFilterFactorTypes = menuFilterFactorTypes;
        return this;
    }

    public ExperimentBuilder withFactorNamesByType(Map<String, String> factorNamesByType) {
        this.factorNamesByType = factorNamesByType;
        return this;
    }

    public ExperimentBuilder withDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    public Experiment create() {
        checkState(CollectionUtils.isNotEmpty(species), "Please provide a non blank species");
        checkState(StringUtils.isNotBlank(description), "Please provide a non blank description");
        checkState(StringUtils.isNotBlank(defaultQueryType), "Please provide a non blank defaultQueryType");
        checkState(CollectionUtils.isNotEmpty(experimentRuns), "Please provide a non empty set of ExperimentRun objects");
        checkState(defaultFilterFactors != null, "Please provide a set of filter factors");
        checkState(menuFilterFactorTypes != null, "Please provide a set of menu filter factor types");

        ExperimentalFactors experimentalFactors = experimentalFactorsBuilder
                .withExperimentRuns(experimentRuns)
                .withMenuFilterFactorTypes(menuFilterFactorTypes)
                .withFactorNamesByType(factorNamesByType)
                .create();

        if (StringUtils.isBlank(displayName)){
            displayName = experimentAccession;
        }

        return new Experiment(experimentalFactors, experimentRuns, description, displayName, species, defaultQueryType, defaultFilterFactors, hasExtraInfoFile);
    }


    public ExperimentBuilder withAccession(String experimentAccession) {
        this.experimentAccession = experimentAccession;
        return this;
    }
}