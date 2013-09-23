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

import com.google.common.collect.Sets;
import org.apache.commons.collections.CollectionUtils;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.model.ExperimentDesign;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.model.differential.AssayGroup;

import java.util.*;

public class BaselineExperiment extends Experiment {

    private String defaultQueryFactorType;

    private Set<Factor> defaultFilterFactors;

    private ExperimentalFactors experimentalFactors;

    private Set<AssayGroup> assayGroups;

    BaselineExperiment(String accession, Date lastUpdate, ExperimentalFactors experimentalFactors,
                       String description,
                       String displayName, Set<String> species, Map<String, String> speciesMapping,
                       String defaultQueryFactorType, Set<Factor> defaultFilterFactors, boolean hasExtraInfoFile,
                       List<String> pubMedIds, ExperimentDesign experimentDesign, Set<AssayGroup> assayGroups) {

        super(ExperimentType.BASELINE, accession, lastUpdate, displayName, description,
                hasExtraInfoFile, species, speciesMapping, pubMedIds, experimentDesign);
        this.experimentalFactors = experimentalFactors;
        this.defaultQueryFactorType = defaultQueryFactorType;
        this.defaultFilterFactors = defaultFilterFactors;
        this.assayGroups = assayGroups;
    }

    public Set<String> getExperimentRunAccessions() {
        Set<String> experimentRuns = Sets.newHashSet();
        for (AssayGroup assayGroup : assayGroups) {
            CollectionUtils.addAll(experimentRuns, assayGroup.iterator());
        }
        return experimentRuns;
    }

    public String getDefaultQueryFactorType() {
        return defaultQueryFactorType;
    }

    public Set<Factor> getDefaultFilterFactors() {
        return Collections.unmodifiableSet(defaultFilterFactors);
    }

    public ExperimentalFactors getExperimentalFactors() {
        return experimentalFactors;
    }

    public Set<AssayGroup> getAssayGroups() {
        return assayGroups;
    }
}
