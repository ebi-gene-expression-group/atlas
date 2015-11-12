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

package uk.ac.ebi.atlas.profiles.baseline;

import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.trader.cache.ProteomicsBaselineExperimentsCache;
import uk.ac.ebi.atlas.trader.loader.ProteomicsBaselineExperimentExpressionLevelFile;

import javax.inject.Inject;
import javax.inject.Named;

import java.util.concurrent.ExecutionException;

import static com.google.common.base.Preconditions.checkState;

@Named
@Scope("prototype")
public class ExpressionsRowDeserializerProteomicsBaselineBuilder extends ExpressionsRowDeserializerBaselineBuilder {

    private String experimentAccession;
    private ProteomicsBaselineExperimentsCache experimentsCache;
    private int[] indicesOfAssayGroups;

    @Inject
    public ExpressionsRowDeserializerProteomicsBaselineBuilder(ProteomicsBaselineExperimentsCache experimentsCache) {
        this.experimentsCache = experimentsCache;
    }

    @Override
    public ExpressionsRowDeserializerProteomicsBaselineBuilder forExperiment(String experimentAccession) {
        this.experimentAccession = experimentAccession;
        return this;
    }

    @Override
    public ExpressionsRowDeserializerProteomicsBaselineBuilder withHeaders(String... tsvFileHeaders) {
        this.indicesOfAssayGroups = ProteomicsBaselineExperimentExpressionLevelFile.indicesOfAssayGroups(tsvFileHeaders);
        return this;
    }

    @Override
    public ExpressionsRowTsvDeserializerBaseline build() {
        try {
            checkState(experimentAccession != null, "Please invoke forExperiment before invoking the build method");

            BaselineExperiment baselineExperiment = experimentsCache.getExperiment(experimentAccession);

            //TODO: ordered factor groups should be passed in from the top, not looked up here
            return new ExpressionsRowTsvDeserializerProteomicsBaseline(baselineExperiment.getExperimentalFactors().getFactorGroupsInOrder(), indicesOfAssayGroups);
        } catch (ExecutionException e) {
            throw new IllegalStateException("Failed to load experiment from cache: " + experimentAccession, e);
        }
    }

}