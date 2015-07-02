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

package uk.ac.ebi.atlas.trader;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.experimentimport.ExperimentDAO;
import uk.ac.ebi.atlas.experimentimport.ExperimentDTO;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.trader.cache.*;

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
    private ProteomicsBaselineExperimentsCache proteomicsBaselineExperimentsCache;
    private ExperimentTypesCache experimentTypesCache;


    @Inject
    public ExperimentTrader(ExperimentDAO experimentDAO,
                            BaselineExperimentsCache baselineExperimentsCache,
                            RnaSeqDiffExperimentsCache rnaSeqDiffExperimentsCache,
                            MicroarrayExperimentsCache microarrayExperimentsCache,
                            ProteomicsBaselineExperimentsCache proteomicsBaselineExperimentsCache,
                            ExperimentTypesCache experimentTypesCache) {

        this.experimentDAO = experimentDAO;
        this.baselineExperimentsCache = baselineExperimentsCache;
        this.rnaSeqDiffExperimentsCache = rnaSeqDiffExperimentsCache;
        this.microarrayExperimentsCache = microarrayExperimentsCache;
        this.proteomicsBaselineExperimentsCache = proteomicsBaselineExperimentsCache;
        this.experimentTypesCache = experimentTypesCache;
    }


    public Experiment getPublicExperiment(String experimentAccession) {
        return getExperimentFromCache(experimentAccession, experimentTypesCache.getExperimentType(experimentAccession));
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
            case RNASEQ_MRNA_BASELINE:
                baselineExperimentsCache.evictExperiment(experimentAccession);
                break;
            case RNASEQ_MRNA_DIFFERENTIAL:
                rnaSeqDiffExperimentsCache.evictExperiment(experimentAccession);
                break;
            case MICROARRAY_1COLOUR_MRNA_DIFFERENTIAL:
            case MICROARRAY_2COLOUR_MRNA_DIFFERENTIAL:
            case MICROARRAY_1COLOUR_MICRORNA_DIFFERENTIAL:
                microarrayExperimentsCache.evictExperiment(experimentAccession);
                break;
            case PROTEOMICS_BASELINE:
                proteomicsBaselineExperimentsCache.evictExperiment(experimentAccession);
                break;
            default:
                throw new IllegalStateException("invalid enum value: " + type);
        }
        experimentTypesCache.evictExperiment(experimentAccession);

    }


    public void removeAllExperimentsFromCache() {
        baselineExperimentsCache.evictAll();
        rnaSeqDiffExperimentsCache.evictAll();
        microarrayExperimentsCache.evictAll();
        proteomicsBaselineExperimentsCache.evictAll();
        experimentTypesCache.evictAll();
    }


    public Experiment getExperimentFromCache(String experimentAccession, ExperimentType experimentType) {

        switch (experimentType) {
            case RNASEQ_MRNA_BASELINE:
                return baselineExperimentsCache.getExperiment(experimentAccession);
            case RNASEQ_MRNA_DIFFERENTIAL:
                return rnaSeqDiffExperimentsCache.getExperiment(experimentAccession);
            case MICROARRAY_1COLOUR_MRNA_DIFFERENTIAL:
            case MICROARRAY_2COLOUR_MRNA_DIFFERENTIAL:
            case MICROARRAY_1COLOUR_MICRORNA_DIFFERENTIAL:
                return microarrayExperimentsCache.getExperiment(experimentAccession);
            case PROTEOMICS_BASELINE:
                return proteomicsBaselineExperimentsCache.getExperiment(experimentAccession);
            default:
                throw new IllegalStateException("invalid enum value: " + experimentType);
        }

    }

    //ToDo (NK): to get Experiment accession we go 4 times to the DB (for each experiment type)

    public Set<String> getBaselineExperimentAccessions() {
        return getPublicExperimentAccessions(ExperimentType.RNASEQ_MRNA_BASELINE);
    }


    public Set<String> getProteomicsBaselineExperimentAccessions() {
        return getPublicExperimentAccessions(ExperimentType.PROTEOMICS_BASELINE);
    }


    public Set<String> getAllBaselineExperimentAccessions() {
        return ImmutableSet.<String>builder().
                addAll(getBaselineExperimentAccessions()).
                addAll(getProteomicsBaselineExperimentAccessions()).
                build();

    }


    public Set<String> getRnaSeqDifferentialExperimentAccessions() {
        return getPublicExperimentAccessions(ExperimentType.RNASEQ_MRNA_DIFFERENTIAL);
    }


    public Set<String> getMicroarrayExperimentAccessions() {
        Set<String> identifiers = Sets.newHashSet();
        identifiers.addAll(getPublicExperimentAccessions(ExperimentType.MICROARRAY_1COLOUR_MRNA_DIFFERENTIAL));
        // as two colour is a subtype of micro array, they need to be added here
        identifiers.addAll((getPublicExperimentAccessions(ExperimentType.MICROARRAY_2COLOUR_MRNA_DIFFERENTIAL)));
        identifiers.addAll((getPublicExperimentAccessions(ExperimentType.MICROARRAY_1COLOUR_MICRORNA_DIFFERENTIAL)));
        return identifiers;
    }


    // Making this method public is part of a work-around until https://www.pivotaltracker.com/story/show/88885788 gets implemented
    public Set<String> getPublicExperimentAccessions(ExperimentType... experimentType) {
        return experimentDAO.findPublicExperimentAccessions(experimentType);
    }

}
