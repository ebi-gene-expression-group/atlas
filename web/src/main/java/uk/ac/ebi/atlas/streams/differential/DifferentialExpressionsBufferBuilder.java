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

package uk.ac.ebi.atlas.streams.differential;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.cache.differential.DifferentialExperimentsCache;
import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.streams.TsvRowBufferBuilder;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static com.google.common.base.Preconditions.checkState;

@Named
@Scope("prototype")
public class DifferentialExpressionsBufferBuilder implements TsvRowBufferBuilder {

    private static final Logger LOGGER = Logger.getLogger(DifferentialExpressionsBufferBuilder.class);

    private String experimentAccession;

    private DifferentialExperimentsCache experimentsCache;

    private List<Contrast> orderedContrasts = new LinkedList<>();

    @Inject
    public DifferentialExpressionsBufferBuilder(DifferentialExperimentsCache experimentsCache) {

        this.experimentsCache = experimentsCache;

    }

    @Override
    public DifferentialExpressionsBufferBuilder forExperiment(String experimentAccession) {

        this.experimentAccession = experimentAccession;

        return this;

    }

    @Override
    public DifferentialExpressionsBufferBuilder withHeaders(String... tsvFileHeaders) {

        LOGGER.debug("<withHeaders> data file headers: " + Arrays.toString(tsvFileHeaders));

        checkState(experimentAccession != null, "Builder not properly initialized!");

        DifferentialExperiment experiment = experimentsCache.getExperiment(experimentAccession);

        List<String> columnHeaders = Arrays.asList(tsvFileHeaders);

        for (String columnHeader : columnHeaders) {
            if (columnHeader.endsWith(".p-value")) {
                String contrastId = StringUtils.substringBefore(columnHeader, ".");
                orderedContrasts.add(experiment.getContrast(contrastId));
            }
        }

        return this;
    }

    @Override
    public DifferentialExpressionsBuffer create() {

        checkState(!orderedContrasts.isEmpty(), "Builder state not ready for creating the ExpressionBuffer");

        return new DifferentialExpressionsBuffer(orderedContrasts);

    }

}