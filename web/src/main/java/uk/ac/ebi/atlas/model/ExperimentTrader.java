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
import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.experimentloader.ExperimentDAO;
import uk.ac.ebi.atlas.experimentloader.ExperimentDTO;
import uk.ac.ebi.atlas.model.cache.baseline.BaselineExperimentsCache;
import uk.ac.ebi.atlas.model.cache.differential.RnaSeqDiffExperimentsCache;
import uk.ac.ebi.atlas.model.cache.microarray.MicroarrayExperimentsCache;
import uk.ac.ebi.atlas.web.ApplicationProperties;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Set;

@Named
@Scope("singleton")
public class ExperimentTrader {

    private BaselineExperimentsCache baselineExperimentsCache;
    private RnaSeqDiffExperimentsCache rnaSeqDiffExperimentsCache;
    private MicroarrayExperimentsCache microarrayExperimentsCache;
    private ExperimentDAO experimentDAO;

    @Inject
    ExperimentTrader(ExperimentDAO experimentDAO,
                     BaselineExperimentsCache baselineExperimentsCache,
                     RnaSeqDiffExperimentsCache rnaSeqDiffExperimentsCache,
                     MicroarrayExperimentsCache microarrayExperimentsCache) {

        this.experimentDAO = experimentDAO;
        this.baselineExperimentsCache = baselineExperimentsCache;
        this.rnaSeqDiffExperimentsCache = rnaSeqDiffExperimentsCache;
        this.microarrayExperimentsCache = microarrayExperimentsCache;
    }

    public Experiment getPublicExperiment(String experimentAccession) {

        ExperimentDTO experimentDTO = experimentDAO.findPublicExperiment(experimentAccession);

        return getExperimentFromCache(experimentAccession, experimentDTO.getExperimentType());

    }

    public Experiment getExperiment(String experimentAccession, String accessKey) {

        if (StringUtils.isBlank(accessKey)){
            return getPublicExperiment(experimentAccession);
        }
        ExperimentDTO experimentDTO = experimentDAO.findExperiment(experimentAccession, accessKey);

        return getExperimentFromCache(experimentAccession, experimentDTO.getExperimentType());
    }


    public void removeExperimentFromCache(String experimentAccession, ExperimentType type) {

        switch (type) {
            case BASELINE:
                baselineExperimentsCache.evictExperiment(experimentAccession);
                break;
            case DIFFERENTIAL:
                rnaSeqDiffExperimentsCache.evictExperiment(experimentAccession);
                break;
            case MICROARRAY:
            case TWOCOLOUR:
            case MICRORNA:
                microarrayExperimentsCache.evictExperiment(experimentAccession);
                break;
            default:
                throw new IllegalStateException("invalid enum value: " + type);
        }

    }

    private Experiment getExperimentFromCache(String experimentAccession, ExperimentType experimentType) {

        switch (experimentType) {
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

    //ToDo (NK): to get Experiment accession we go 4 times to the DB (for each experiment type)

    public Set<String> getBaselineExperimentAccessions() {
        return getPublicExperimentAccessions(ExperimentType.BASELINE);
    }

    public Set<String> getDifferentialExperimentAccessions() {
        return getPublicExperimentAccessions(ExperimentType.DIFFERENTIAL);
    }

    public Set<String> getMicroarrayExperimentAccessions() {
        Set<String> identifiers = Sets.newHashSet();
        identifiers.addAll(getPublicExperimentAccessions(ExperimentType.MICROARRAY));
        // as two colour is a subtype of micro array, they need to be added here
        identifiers.addAll((getPublicExperimentAccessions(ExperimentType.TWOCOLOUR)));
        identifiers.addAll((getPublicExperimentAccessions(ExperimentType.MICRORNA)));
        return identifiers;
    }

    Set<String> getPublicExperimentAccessions(ExperimentType... experimentType) {
        return experimentDAO.findPublicExperimentAccessions(experimentType);
    }

}
