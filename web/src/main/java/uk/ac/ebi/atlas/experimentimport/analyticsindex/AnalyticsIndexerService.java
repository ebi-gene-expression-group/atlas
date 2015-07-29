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

package uk.ac.ebi.atlas.experimentimport.analyticsindex;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.experimentimport.analyticsindex.baseline.BaselineAnalyticsIndexerService;
import uk.ac.ebi.atlas.experimentimport.analyticsindex.differential.DiffAnalyticsIndexerService;
import uk.ac.ebi.atlas.experimentimport.analyticsindex.differential.MicroArrayDiffAnalyticsIndexerService;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.trader.ExperimentTrader;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@Scope("singleton")
public class AnalyticsIndexerService {
    private static final Logger LOGGER = Logger.getLogger(AnalyticsIndexerService.class);

    private final AnalyticsIndexDao analyticsIndexDao;
    private final BaselineAnalyticsIndexerService baselineAnalyticsIndexerService;
    private final DiffAnalyticsIndexerService diffAnalyticsIndexerService;
    private final MicroArrayDiffAnalyticsIndexerService microArrayDiffAnalyticsIndexerService;

    @Inject
    public AnalyticsIndexerService(AnalyticsIndexDao analyticsIndexDao, BaselineAnalyticsIndexerService baselineAnalyticsIndexerService, DiffAnalyticsIndexerService diffAnalyticsIndexerService, MicroArrayDiffAnalyticsIndexerService microArrayDiffAnalyticsIndexerService, ExperimentTrader experimentTrader) {
        this.analyticsIndexDao = analyticsIndexDao;
        this.baselineAnalyticsIndexerService = baselineAnalyticsIndexerService;
        this.diffAnalyticsIndexerService = diffAnalyticsIndexerService;
        this.microArrayDiffAnalyticsIndexerService = microArrayDiffAnalyticsIndexerService;
    }

    public int index(Experiment experiment, int batchSize) {
       ExperimentType experimentType = experiment.getType();

        if (experimentType == ExperimentType.RNASEQ_MRNA_BASELINE) {
            return baselineAnalyticsIndexerService.index((BaselineExperiment) experiment, batchSize);
        } else if (experimentType == ExperimentType.PROTEOMICS_BASELINE) {
            return baselineAnalyticsIndexerService.index((BaselineExperiment) experiment, batchSize);
        } else if (experimentType == ExperimentType.RNASEQ_MRNA_DIFFERENTIAL) {
            return diffAnalyticsIndexerService.index((DifferentialExperiment) experiment, batchSize);
        } else if (experimentType == ExperimentType.MICROARRAY_1COLOUR_MICRORNA_DIFFERENTIAL ||
                   experimentType == ExperimentType.MICROARRAY_1COLOUR_MRNA_DIFFERENTIAL ||
                   experimentType == ExperimentType.MICROARRAY_2COLOUR_MRNA_DIFFERENTIAL) {
            return microArrayDiffAnalyticsIndexerService.index((MicroarrayExperiment) experiment, batchSize);
        }

        throw new UnsupportedOperationException("No analytics loader for experiment type " + experimentType);
    }

    // synchronized necessary because analyticsIndexDao#delete does an explicit commit
    public synchronized void deleteExperimentFromIndex(String accession) {
        LOGGER.info("Deleting documents for " + accession);
        analyticsIndexDao.deleteDocumentsForExperiment(accession);
        LOGGER.info("Done deleting documents for " + accession);
    }
}