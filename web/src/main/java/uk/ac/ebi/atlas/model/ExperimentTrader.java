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

import uk.ac.ebi.atlas.model.cache.baseline.BaselineExperimentsCache;
import uk.ac.ebi.atlas.model.cache.differential.RnaSeqDiffExperimentsCache;
import uk.ac.ebi.atlas.model.cache.microarray.MicroarrayExperimentsCache;
import uk.ac.ebi.atlas.web.ApplicationProperties;
import uk.ac.ebi.atlas.web.controllers.ResourceNotFoundException;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class ExperimentTrader {

    private BaselineExperimentsCache baselineExperimentsCache;
    private RnaSeqDiffExperimentsCache rnaSeqDiffExperimentsCache;
    private MicroarrayExperimentsCache microarrayExperimentsCache;
    private ApplicationProperties applicationProperties;

    @Inject
    ExperimentTrader(ApplicationProperties applicationProperties,
                     BaselineExperimentsCache baselineExperimentsCache,
                     RnaSeqDiffExperimentsCache rnaSeqDiffExperimentsCache,
                     MicroarrayExperimentsCache microarrayExperimentsCache){

        this.applicationProperties = applicationProperties;
        this.baselineExperimentsCache = baselineExperimentsCache;
        this.rnaSeqDiffExperimentsCache = rnaSeqDiffExperimentsCache;
        this.microarrayExperimentsCache = microarrayExperimentsCache;
    }

    public Experiment getExperiment(String experimentAccession){

        if (applicationProperties.getBaselineExperimentsIdentifiers().contains(experimentAccession)) {
            return baselineExperimentsCache.getExperiment(experimentAccession);
        }
        if (applicationProperties.getDifferentialExperimentsIdentifiers().contains(experimentAccession)) {
            return rnaSeqDiffExperimentsCache.getExperiment(experimentAccession);
        }
        if (applicationProperties.getMicroarrayExperimentsIdentifiers().contains(experimentAccession)) {
            return microarrayExperimentsCache.getExperiment(experimentAccession);
        }
        throw new ResourceNotFoundException("Experiment not found with accession: " + experimentAccession);

    }


}
