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

import uk.ac.ebi.atlas.model.AssayGroups;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.model.ExperimentDesign;
import uk.ac.ebi.atlas.model.ExperimentType;

import java.util.*;

public class BaselineExperiment extends Experiment {

    private ExperimentalFactors experimentalFactors;

    private AssayGroups assayGroups;

    private String dataProviderURL;
    private String dataProviderDescription;

    BaselineExperiment(String accession, Date lastUpdate, ExperimentalFactors experimentalFactors,
                       String description, String displayName,
                       Set<String> organisms, String kingdom, Map<String, String> speciesMapping,
                       boolean hasExtraInfoFile, Set<String> pubMedIds,
                       ExperimentDesign experimentDesign, AssayGroups assayGroups) {
        this(accession, lastUpdate, experimentalFactors, description, displayName, organisms, kingdom, speciesMapping,
                hasExtraInfoFile, pubMedIds, experimentDesign,  assayGroups, "", "");
    }

    BaselineExperiment(String accession, Date lastUpdate, ExperimentalFactors experimentalFactors,
                       String description, String displayName,
                       Set<String> organisms, String kingdom, Map<String, String> speciesMapping,
                       boolean hasExtraInfoFile, Set<String> pubMedIds,
                       ExperimentDesign experimentDesign, AssayGroups assayGroups, String dataProviderURL, String dataProviderDescription) {
        this(ExperimentType.RNASEQ_MRNA_BASELINE, accession, lastUpdate, experimentalFactors,
                description, displayName, organisms, kingdom, speciesMapping,
                hasExtraInfoFile, pubMedIds,
                experimentDesign, assayGroups, dataProviderURL, dataProviderDescription);
    }

    BaselineExperiment(ExperimentType experimentType, String accession, Date lastUpdate, ExperimentalFactors experimentalFactors,
                       String description, String displayName,
                       Set<String> organisms, String kingdom, Map<String, String> speciesMapping,
                       boolean hasExtraInfoFile, Set<String> pubMedIds,
                       ExperimentDesign experimentDesign, AssayGroups assayGroups) {
        this(experimentType, accession, lastUpdate, experimentalFactors, description, displayName, organisms, kingdom, speciesMapping,
                hasExtraInfoFile, pubMedIds, experimentDesign, assayGroups, "", "");
    }

    BaselineExperiment(ExperimentType experimentType, String accession, Date lastUpdate, ExperimentalFactors experimentalFactors,
            String description, String displayName,
            Set < String > organisms, String kingdom, Map < String, String > speciesMapping,
        boolean hasExtraInfoFile, Set<String> pubMedIds,
                       ExperimentDesign experimentDesign, AssayGroups assayGroups, String dataProviderURL, String dataProviderDescription) {

        super(experimentType, accession, lastUpdate, displayName, description,
                hasExtraInfoFile, organisms, kingdom, speciesMapping, pubMedIds, experimentDesign);
        this.experimentalFactors = experimentalFactors;
        this.assayGroups = assayGroups;
        this.dataProviderURL = dataProviderURL;
        this.dataProviderDescription = dataProviderDescription;
    }

    public Set<String> getExperimentRunAccessions() {
        return assayGroups.getAssayAccessions();
    }

    public ExperimentalFactors getExperimentalFactors() {
        return experimentalFactors;
    }

    public AssayGroups getAssayGroups() {
        return assayGroups;
    }

    public String getDataProviderURL() {
        return dataProviderURL;
    }

    public String getDataProviderDescription() {
        return dataProviderDescription;
    }


    public SortedSet<Factor> getAssayGroupFactors(Collection<String> assayGroupIds, String factorType) {
        return getExperimentalFactors().getFactors(assayGroupIds, factorType);
    }

    public boolean isTissueExperiment() {
        return getExperimentalFactors().getDefaultQueryFactorType().equals("ORGANISM_PART");
    }

}
