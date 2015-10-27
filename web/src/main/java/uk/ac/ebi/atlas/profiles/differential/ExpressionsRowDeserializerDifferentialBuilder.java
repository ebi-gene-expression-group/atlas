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

package uk.ac.ebi.atlas.profiles.differential;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.util.CollectionUtils;
import uk.ac.ebi.atlas.model.Expression;
import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.profiles.ExpressionsRowTsvDeserializer;
import uk.ac.ebi.atlas.profiles.ExpressionsRowDeserializerBuilder;
import uk.ac.ebi.atlas.trader.cache.ExperimentsCache;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static com.google.common.base.Preconditions.checkState;

public abstract class ExpressionsRowDeserializerDifferentialBuilder<T extends Expression, K extends DifferentialExperiment> implements ExpressionsRowDeserializerBuilder<String, T> {

    private static final Logger LOGGER = LogManager.getLogger(ExpressionsRowDeserializerDifferentialBuilder.class);
    private ExperimentsCache<K> experimentsCache;
    private String experimentAccession;
    private List<Contrast> orderedContrasts;

    public ExpressionsRowDeserializerDifferentialBuilder(ExperimentsCache<K> experimentsCache) {
        this.experimentsCache = experimentsCache;
    }

    @Override
    public ExpressionsRowDeserializerDifferentialBuilder forExperiment(String experimentAccession) {

        this.experimentAccession = experimentAccession;

        return this;

    }

    @Override
    public ExpressionsRowDeserializerDifferentialBuilder withHeaders(String... tsvFileHeaders) {

        LOGGER.debug("<withHeaders> data file headers: " + Arrays.toString(tsvFileHeaders));

        checkState(experimentAccession != null, "Builder not properly initialized!");

        DifferentialExperiment experiment = experimentsCache.getExperiment(experimentAccession);

        List<String> columnHeaders = Arrays.asList(tsvFileHeaders);

        orderedContrasts = new LinkedList<>();
        for (String columnHeader : columnHeaders) {
            if (columnHeader.endsWith(".p-value")) {
                String contrastId = StringUtils.substringBefore(columnHeader, ".");
                orderedContrasts.add(experiment.getContrast(contrastId));
            }
        }

        return this;
    }

    @Override
    public ExpressionsRowTsvDeserializer<T> build() {

        checkState(!CollectionUtils.isEmpty(orderedContrasts), "Builder state not ready for creating the ExpressionBuffer");

        return getBufferInstance(orderedContrasts);

    }

    protected abstract ExpressionsRowTsvDeserializer<T> getBufferInstance(List<Contrast> orderedContrasts);
}
