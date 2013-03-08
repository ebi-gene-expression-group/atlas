/*
 * Copyright 2008-2012 Microarray Informatics Team, EMBL-European Bioinformatics Institute
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
import com.google.common.collect.Iterables;
import com.google.common.collect.Ordering;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.baseline.BaselineExpression;
import uk.ac.ebi.atlas.model.baseline.ExperimentRun;
import uk.ac.ebi.atlas.model.baseline.FactorGroup;
import uk.ac.ebi.atlas.model.cache.baseline.BaselineExperimentsCache;
import uk.ac.ebi.atlas.streams.TsvRowBuffer;
import uk.ac.ebi.atlas.streams.TsvRowBufferBuilder;

import javax.inject.Inject;
import javax.inject.Named;
import java.text.MessageFormat;
import java.util.*;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

public class BaselineExpressionsBuffer extends TsvRowBuffer<BaselineExpression> {

    private static final Logger logger = Logger.getLogger(BaselineExpressionsBuffer.class);

    private static final String FACTOR_NOT_FOUND = "Factor value not found for Run Accessions {0} and Experiment Accession {1}";

    private Iterator<FactorGroup> expectedFactorGroups;

    protected BaselineExpressionsBuffer(List<FactorGroup> orderedFactorGroups) {
        this.expectedFactorGroups = Iterables.cycle(orderedFactorGroups).iterator();
    }


    @Override
    public BaselineExpression poll() {
        String expressionLevelString = expressionLevelsBuffer.poll();

        if (expressionLevelString == null) {
            return null;
        }
        double expressionLevel = Double.parseDouble(expressionLevelString);

        return new BaselineExpression(expressionLevel, expectedFactorGroups.next());
    }

    @Named
    @Scope("prototype")
    public static class Builder implements TsvRowBufferBuilder<BaselineExpressionsBuffer> {

        private String experimentAccession;

        private BaselineExperimentsCache experimentsCache;

        private List<FactorGroup> orderedFactorGroups = new LinkedList<>();

        private boolean readyToCreate;
        private static final String EXPERIMENT_RUN_NOT_FOUND = "ExperimentRun {0} not found for Experiment {1}";

        @Inject
        public Builder(BaselineExperimentsCache experimentsCache) {

            this.experimentsCache = experimentsCache;

        }

        @Override
        public Builder forExperiment(String experimentAccession) {

            this.experimentAccession = experimentAccession;

            return this;

        }

        @Override
        public Builder withHeaders(String... tsvFileHeaders) {

            logger.debug("<withHeaders> data file headers: " + Arrays.toString(tsvFileHeaders));

            checkState(experimentAccession != null, "Builder not properly initialized!");

            List<String> columnHeaders = Arrays.asList(ArrayUtils.remove(tsvFileHeaders, GENE_ID_COLUMN));

            for (String columnHeader : columnHeaders) {

                orderedFactorGroups.add(getFactorGroup(columnHeader, experimentAccession));

            }
            readyToCreate = true;

            return this;
        }


        private FactorGroup getFactorGroup(String columnHeader, String experimentAccession) {

            String[] columnRuns = columnHeader.split(",");

            //ToDo: no need to have loop, return after the first iteration
            for (String columnRun : columnRuns) {
                columnRun = columnRun.trim();

                BaselineExperiment experiment = experimentsCache.getExperiment(experimentAccession);
                checkNotNull(experiment, MessageFormat.format(EXPERIMENT_RUN_NOT_FOUND, columnRun, experimentAccession));

                return experiment.getFactorGroup(columnRun);
            }

            throw new IllegalStateException(MessageFormat.format(FACTOR_NOT_FOUND, columnHeader, experimentAccession));
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
}
