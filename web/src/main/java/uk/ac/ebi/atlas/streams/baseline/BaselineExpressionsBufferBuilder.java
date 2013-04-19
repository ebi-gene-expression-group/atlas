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

package uk.ac.ebi.atlas.streams.baseline;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.baseline.ExperimentRun;
import uk.ac.ebi.atlas.model.baseline.FactorGroup;
import uk.ac.ebi.atlas.model.cache.baseline.BaselineExperimentsCache;
import uk.ac.ebi.atlas.streams.TsvRowBufferBuilder;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import static com.google.common.base.Preconditions.checkState;

@Named
@Scope("prototype")
public class BaselineExpressionsBufferBuilder implements TsvRowBufferBuilder<BaselineExpressionsBuffer> {

    private String experimentAccession;

    private BaselineExperimentsCache experimentsCache;

    @Inject
    public BaselineExpressionsBufferBuilder(BaselineExperimentsCache experimentsCache) {

        this.experimentsCache = experimentsCache;

    }

    @Override
    public BaselineExpressionsBufferBuilder forExperiment(String experimentAccession) {

        this.experimentAccession = experimentAccession;

        return this;

    }

    @Override
    public BaselineExpressionsBufferBuilder withHeaders(String... tsvFileHeaders) {
        //We don't need to process the headers for Baseline
        //because orderedFactorGroups is already available from BaselineExperiment
        return this;
    }

    @Override
    public BaselineExpressionsBuffer build() {
        checkState(experimentAccession != null, "Please invoke forExperiment before invoking the build method");

        BaselineExperiment baselineExperiment = experimentsCache.getExperiment(experimentAccession);

        return new BaselineExpressionsBuffer(baselineExperiment.getExperimentalFactors().getOrderedFactorGroups());

    }

    Predicate<ExperimentRun> isExperimentRunRequired(final List<String> orderedRunAccessions) {
        return new Predicate<ExperimentRun>() {
            @Override
            public boolean apply(ExperimentRun experimentRun) {
                return orderedRunAccessions.contains(experimentRun.getAccession());
            }
        };
    }

}