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
import uk.ac.ebi.atlas.model.AssayGroups;
import uk.ac.ebi.atlas.model.ExperimentDesign;

import javax.inject.Named;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

@Named
@Scope("prototype")
public class BaselineExperimentBuilder {

    private Set<String> organisms;
    private String kingdom;
    private String description;
    private String dataProviderURL;
    private String dataProviderDescription;
    private String displayName;
    private boolean hasExtraInfoFile;
    private Map<String, String> speciesMapping;
    private String experimentAccession;
    private Set<String> pubMedIds;
    private ExperimentDesign experimentDesign;
    private Date lastUpdate;
    private AssayGroups assayGroups;
    private ExperimentalFactors experimentalFactors;

    public BaselineExperimentBuilder forOrganisms(Set<String> organisms) {
        this.organisms = organisms;
        return this;
    }

    public BaselineExperimentBuilder ofKingdom(String kingdom) {
        this.kingdom = kingdom;
        return this;
    }

    public BaselineExperimentBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public BaselineExperimentBuilder withDataProviderURL(String dataProviderURL) {
        this.dataProviderURL = dataProviderURL;
        return this;
    }

    public BaselineExperimentBuilder withDataProviderDescription(String dataProviderDescription) {
        this.dataProviderDescription = dataProviderDescription;
        return this;
    }

    public BaselineExperimentBuilder withExtraInfo(boolean hasExtraInfoFile) {
        this.hasExtraInfoFile = hasExtraInfoFile;
        return this;
    }

    public BaselineExperimentBuilder withExperimentalFactors(ExperimentalFactors experimentalFactors) {
        this.experimentalFactors = experimentalFactors;
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

    public BaselineExperimentBuilder withPubMedIds(Set<String> pubMedIds) {
        this.pubMedIds = pubMedIds;
        return this;
    }

    public BaselineExperimentBuilder withExperimentDesign(ExperimentDesign experimentDesign) {
        this.experimentDesign = experimentDesign;
        return this;
    }

    public BaselineExperimentBuilder withAssayGroups(AssayGroups assayGroups) {
        this.assayGroups = assayGroups;
        return this;
    }

    public BaselineExperimentBuilder withAccession(String experimentAccession) {
        this.experimentAccession = experimentAccession;
        return this;
    }

    public BaselineExperimentBuilder withLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
        return this;
    }

    public BaselineExperiment create() {
        validate();

        return new BaselineExperiment(experimentAccession, lastUpdate, experimentalFactors, description,
                displayName, organisms, kingdom, speciesMapping, hasExtraInfoFile,
                pubMedIds, experimentDesign, assayGroups, dataProviderURL, dataProviderDescription);
    }

    public ProteomicsBaselineExperiment createProteomics() {
        validate();

        return new ProteomicsBaselineExperiment(experimentAccession, lastUpdate, experimentalFactors, description,
                displayName, organisms, kingdom, speciesMapping, hasExtraInfoFile,
                pubMedIds, experimentDesign, assayGroups, dataProviderURL, dataProviderDescription);
    }

    void validate() {
        checkNotNull(assayGroups, "Please provide a non empty set of AssayGroup objects");
        checkState(CollectionUtils.isNotEmpty(assayGroups.getAssayGroupIds()), "Please provide a non empty set of AssayGroup objects");
        checkState(speciesMapping != null, "Please provide a map of species mappings");
        checkState(experimentalFactors != null, "Please provide a ExperimentFactors object");
        checkState(experimentDesign != null, "Please provide a ExperimentDesign object");
        checkState(pubMedIds != null, "Please provide a pubMedIds object");

        if (StringUtils.isBlank(displayName)) {
            displayName = experimentAccession;
        }
    }

}