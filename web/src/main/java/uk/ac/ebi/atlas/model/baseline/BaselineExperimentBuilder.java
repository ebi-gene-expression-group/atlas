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

package uk.ac.ebi.atlas.model.baseline;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.ExperimentDesign;
import uk.ac.ebi.atlas.model.differential.AssayGroup;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.google.common.base.Preconditions.checkState;

@Named
@Scope("prototype")
public class BaselineExperimentBuilder {

    private Set<String> species;
    private String description;
    private String displayName;
    private String defaultQueryType;
    private boolean hasExtraInfoFile;
    private Set<Factor> defaultFilterFactors;
    private Set<String> menuFilterFactorTypes;
    private ExperimentalFactorsBuilder experimentalFactorsBuilder;
    private Map<String, String> factorNamesByType;
    private Map<String, String> speciesMapping;
    private String experimentAccession;
    private List<FactorGroup> orderedFactorGroups;
    private List<String> pubMedIds;
    private ExperimentDesign experimentDesign;
    private Date lastUpdate;
    private String investigationTitle;
    private Set<AssayGroup> assayGroups;

    @Inject
    BaselineExperimentBuilder(ExperimentalFactorsBuilder experimentalFactorsBuilder) {
        this.experimentalFactorsBuilder = experimentalFactorsBuilder;
    }

    public BaselineExperimentBuilder forSpecies(Set<String> species) {
        this.species = species;
        return this;
    }

    public BaselineExperimentBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public BaselineExperimentBuilder withDefaultQueryType(String defaultQueryType) {
        this.defaultQueryType = defaultQueryType;
        return this;
    }

    public BaselineExperimentBuilder withDefaultFilterFactors(Set<Factor> defaultFilterFactors) {
        this.defaultFilterFactors = defaultFilterFactors;
        return this;
    }

    public BaselineExperimentBuilder withExtraInfo(boolean hasExtraInfoFile) {
        this.hasExtraInfoFile = hasExtraInfoFile;
        return this;
    }

    public BaselineExperimentBuilder withMenuFilterFactorTypes(Set<String> menuFilterFactorTypes) {
        this.menuFilterFactorTypes = menuFilterFactorTypes;
        return this;
    }

    public BaselineExperimentBuilder withFactorNamesByType(Map<String, String> factorNamesByType) {
        this.factorNamesByType = factorNamesByType;
        return this;
    }

    public BaselineExperimentBuilder withDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    public BaselineExperimentBuilder withSpeciesMapping(Map<String, String> speciesMapping) {
        this.speciesMapping = speciesMapping;
        return this;
    }

    public BaselineExperimentBuilder withPubMedIds(List<String> pubMedIds) {
        this.pubMedIds = pubMedIds;
        return this;
    }

    public BaselineExperimentBuilder withExperimentDesign(ExperimentDesign experimentDesign) {
        this.experimentDesign = experimentDesign;
        return this;
    }

    public BaselineExperimentBuilder withAssayGroups(Set<AssayGroup> assayGroups) {
        this.assayGroups = assayGroups;
        return this;
    }

    public BaselineExperiment create() {
        checkState(CollectionUtils.isNotEmpty(species), "Please provide a non blank species");
        checkState(StringUtils.isNotBlank(description), "Please provide a non blank description");
        checkState(StringUtils.isNotBlank(defaultQueryType), "Please provide a non blank defaultQueryType");
        checkState(CollectionUtils.isNotEmpty(assayGroups), "Please provide a non empty set of AssayGroup objects");
        checkState(defaultFilterFactors != null, "Please provide a set of filter factors");
        checkState(menuFilterFactorTypes != null, "Please provide a set of menu filter factor types");
        checkState(speciesMapping != null, "Please provide a map of species mappings");
        checkState(CollectionUtils.isNotEmpty(pubMedIds), "Please provide a non blank pubMedIds");
        checkState(experimentDesign != null, "Please provide a ExperimentDesign object");

        ExperimentalFactors experimentalFactors = experimentalFactorsBuilder
                .withOrderedFactorGroups(orderedFactorGroups)
                .withMenuFilterFactorTypes(menuFilterFactorTypes)
                .withFactorNamesByType(factorNamesByType)
                .create();

        if (StringUtils.isBlank(displayName)) {
            displayName = experimentAccession;
        }

        return new BaselineExperiment(experimentAccession, lastUpdate, experimentalFactors, description,
                displayName, species, speciesMapping, defaultQueryType, defaultFilterFactors, hasExtraInfoFile,
                pubMedIds, experimentDesign, assayGroups);
    }


    public BaselineExperimentBuilder withAccession(String experimentAccession) {
        this.experimentAccession = experimentAccession;
        return this;
    }

    public BaselineExperimentBuilder withOrderedFactorGroups(List<FactorGroup> orderedFactorGroups) {
        this.orderedFactorGroups = orderedFactorGroups;
        return this;
    }

    public BaselineExperimentBuilder withLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
        return this;
    }

    public BaselineExperimentBuilder withInvestigationTitle(String investigationTitle) {
        this.investigationTitle = investigationTitle;
        return this;
    }
}