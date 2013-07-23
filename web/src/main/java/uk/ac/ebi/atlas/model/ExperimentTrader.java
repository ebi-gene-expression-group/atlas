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

import com.google.common.collect.Sets;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.experimentloader.ExperimentConfiguration;
import uk.ac.ebi.atlas.experimentloader.ExperimentConfigurationDao;
import uk.ac.ebi.atlas.model.cache.baseline.BaselineExperimentsCache;
import uk.ac.ebi.atlas.model.cache.differential.RnaSeqDiffExperimentsCache;
import uk.ac.ebi.atlas.model.cache.microarray.MicroarrayExperimentsCache;
import uk.ac.ebi.atlas.web.ApplicationProperties;
import uk.ac.ebi.atlas.web.controllers.ResourceNotFoundException;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Set;

@Named
@Scope("singleton")
public class ExperimentTrader {

    private BaselineExperimentsCache baselineExperimentsCache;
    private RnaSeqDiffExperimentsCache rnaSeqDiffExperimentsCache;
    private MicroarrayExperimentsCache microarrayExperimentsCache;
    private ExperimentConfigurationDao experimentConfigurationDao;
    private ApplicationProperties applicationProperties;

    @Inject
    ExperimentTrader(ExperimentConfigurationDao experimentConfigurationDao,
                     ApplicationProperties applicationProperties,
                     BaselineExperimentsCache baselineExperimentsCache,
                     RnaSeqDiffExperimentsCache rnaSeqDiffExperimentsCache,
                     MicroarrayExperimentsCache microarrayExperimentsCache){

        this.experimentConfigurationDao = experimentConfigurationDao;
        this.applicationProperties = applicationProperties;
        this.baselineExperimentsCache = baselineExperimentsCache;
        this.rnaSeqDiffExperimentsCache = rnaSeqDiffExperimentsCache;
        this.microarrayExperimentsCache = microarrayExperimentsCache;
    }

    public Experiment getExperiment(String experimentAccession){

        ExperimentConfiguration experimentConfiguration = experimentConfigurationDao.getExperimentConfiguration(experimentAccession);

        if (experimentConfiguration == null) {

            throw new ResourceNotFoundException("Experiment not found with accession: " + experimentAccession);

        }

        return getExperimentFromCache(experimentAccession, experimentConfiguration.getExperimentType());

    }

    private Experiment getExperimentFromCache(String experimentAccession, ExperimentType experimentType) {

        switch(experimentType){
            case BASELINE:
                return baselineExperimentsCache.getExperiment(experimentAccession);
            case DIFFERENTIAL:
                return rnaSeqDiffExperimentsCache.getExperiment(experimentAccession);
            case MICROARRAY:
            case TWOCOLOUR:
            case MICRORNA:
                return microarrayExperimentsCache.getExperiment(experimentAccession);
            default:
                throw new IllegalStateException("invalid enum value: " + experimentType);
        }

    }

    public Set<String> getBaselineExperimentsIdentifiers() {
        return getExperimentIdentifiersForType(ExperimentType.BASELINE);
    }

    public Set<String> getDifferentialExperimentsIdentifiers() {
        return getExperimentIdentifiersForType(ExperimentType.DIFFERENTIAL);
    }

    public Set<String> getMicroarrayExperimentsIdentifiers() {
        Set<String> identifiers = Sets.newHashSet();
        identifiers.addAll(getExperimentIdentifiersForType(ExperimentType.MICROARRAY));
        // as two colour is a subtype of micro array, they need to be added here
        identifiers.addAll((getExperimentIdentifiersForType(ExperimentType.TWOCOLOUR)));
        identifiers.addAll((getExperimentIdentifiersForType(ExperimentType.MICRORNA)));
        return identifiers;
    }

    private Set<String> getExperimentIdentifiersForType(ExperimentType... experimentType) {
        Set<String> experimentAccessions = experimentConfigurationDao.getExperimentAccessions(experimentType);

        // this filtering is for integration tests using only subset of all experiments
        Set<String> testCaseExperimentAccession = applicationProperties.getTestCaseExperimentAccessions();
        if (CollectionUtils.isNotEmpty(testCaseExperimentAccession)) {
            return Sets.intersection(experimentAccessions, testCaseExperimentAccession);
        }
        return experimentAccessions;
    }




}
