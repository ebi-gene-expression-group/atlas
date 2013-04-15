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

    private static final Logger LOGGER = Logger.getLogger(BaselineExpressionsBufferBuilder.class);

    private String experimentAccession;

    private BaselineExperimentsCache experimentsCache;

    private List<FactorGroup> orderedFactorGroups = new LinkedList<>();

    private boolean readyToCreate;

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

        LOGGER.debug("<withHeaders> data file headers: " + Arrays.toString(tsvFileHeaders));

        checkState(experimentAccession != null, "Builder not properly initialized!");

        List<String> columnHeaders = Arrays.asList(tsvFileHeaders);

        initOrderedFactorGroups(columnHeaders);

        readyToCreate = true;

        return this;
    }

    protected void initOrderedFactorGroups(List<String> columnHeaders) {

        List<String> firstRunAccessions = Lists.newArrayList(Collections2.transform(columnHeaders, new Function<String, String>() {
            @Override
            public String apply(String columnHeader) {
                return StringUtils.substringBefore(columnHeader, ",").trim();
            }
        }));

        BaselineExperiment experiment = experimentsCache.getExperiment(experimentAccession);
        orderedFactorGroups = experiment.createOrderedFactorGroups(firstRunAccessions);

    }


    @Override
    public BaselineExpressionsBuffer create() {

        checkState(readyToCreate, "Builder state not ready for creating the ExpressionBuffer");

        return new BaselineExpressionsBuffer(orderedFactorGroups);

    }


    Predicate<ExperimentRun> isExperimentRunRequired(final List<String> orderedRunAccessions) {
        return new Predicate<ExperimentRun>() {
            @Override
            public boolean apply(ExperimentRun experimentRun) {
                return orderedRunAccessions.contains(experimentRun.getAccession());
            }
        };
    }


    Comparator<ExperimentRun> experimentRunComparator(final List<String> orderedRunAccessions) {

        return Ordering.natural().onResultOf(new Function<ExperimentRun, Integer>() {
            @Override
            public Integer apply(ExperimentRun experimentRun) {
                int orderIndexOfRun = orderedRunAccessions.indexOf(experimentRun.getAccession());
                checkState(orderIndexOfRun >= 0, "Illegal state, experimentRun with accession = " + experimentRun.getAccession() + "is not included in ordered run accessions : " + orderedRunAccessions);
                return orderIndexOfRun;
            }
        });
    }

}