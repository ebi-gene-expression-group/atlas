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
import uk.ac.ebi.atlas.model.baseline.BaselineExpression;
import uk.ac.ebi.atlas.profiles.TsvRowQueueBuilder;
import uk.ac.ebi.atlas.trader.cache.BaselineExperimentsCache;

import javax.inject.Inject;
import javax.inject.Named;

import static com.google.common.base.Preconditions.checkState;

@Named
@Scope("prototype")
public class BaselineExpressionsQueueBuilder implements TsvRowQueueBuilder<BaselineExpression> {

    private String experimentAccession;

    private BaselineExperimentsCache experimentsCache;

    @Inject
    public BaselineExpressionsQueueBuilder(BaselineExperimentsCache experimentsCache) {

        this.experimentsCache = experimentsCache;

    }

    @Override
    public BaselineExpressionsQueueBuilder forExperiment(String experimentAccession) {

        this.experimentAccession = experimentAccession;

        return this;

    }

    @Override
    public BaselineExpressionsQueueBuilder withHeaders(String... tsvFileHeaders) {
        //We don't need to process the headers for Baseline
        //because orderedFactorGroups is already available from BaselineExperiment
        return this;
    }

    @Override
    public BaselineExpressionsQueue build() {
        checkState(experimentAccession != null, "Please invoke forExperiment before invoking the build method");

        BaselineExperiment baselineExperiment = experimentsCache.getExperiment(experimentAccession);

        //TODO: maybe we should use what we get from withHeaders - then we can remove dependency on experimentsCache
        return new BaselineExpressionsQueue(baselineExperiment.getExperimentalFactors().getOrderedFactorGroups());

    }

}