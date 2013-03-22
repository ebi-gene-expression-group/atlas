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

package uk.ac.ebi.atlas.streams.differential;

import com.google.common.collect.Iterables;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.cache.differential.DifferentialExperimentsCache;
import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.model.differential.DifferentialExpression;
import uk.ac.ebi.atlas.streams.TsvRowBuffer;
import uk.ac.ebi.atlas.streams.TsvRowBufferBuilder;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;

import static com.google.common.base.Preconditions.checkState;

public class DifferentialExpressionsBuffer extends TsvRowBuffer<DifferentialExpression> {

    private static final Logger logger = Logger.getLogger(DifferentialExpressionsBuffer.class);

    private Queue<String> differentialExpressionLevelsBuffer = new LinkedList<>();

    private Iterator<Contrast> expectedContrasts;

    public static final int GENE_ID_COLUMN = 0;


    DifferentialExpressionsBuffer(List<Contrast> orderedContrasts) {
        this.expectedContrasts = Iterables.cycle(orderedContrasts).iterator();
    }

    public DifferentialExpression poll() {
        String pValueString = differentialExpressionLevelsBuffer.poll();
        if (pValueString == null) {
            return null;
        }
        String foldChangeString = differentialExpressionLevelsBuffer.poll();
        if ("NA".equalsIgnoreCase(pValueString)){
            return poll();
        }
        checkState(foldChangeString != null, "missing fold change column in the analytics file");
        double pValue = parseDouble(pValueString);
        double foldChange = parseDouble(foldChangeString);

        return new DifferentialExpression(pValue, foldChange, expectedContrasts.next());
    }

    double parseDouble(String value) {
        if(value.equalsIgnoreCase("inf")){
            return Double.POSITIVE_INFINITY;
        }
        if(value.equalsIgnoreCase("-inf")){
            return Double.NEGATIVE_INFINITY;
        }
        return Double.parseDouble(value);
    }

    public DifferentialExpressionsBuffer reload(String... values) {
        checkState(this.differentialExpressionLevelsBuffer.isEmpty(), "Reload must be invoked only when readNext returns null");

        Collections.addAll(this.differentialExpressionLevelsBuffer, values);

        differentialExpressionLevelsBuffer.poll(); //removing geneName value

        return this;
    }

    @Named
    @Scope("prototype")
    public static class Builder implements TsvRowBufferBuilder {

        private String experimentAccession;

        private DifferentialExperimentsCache experimentsCache;

        private List<Contrast> orderedContrasts = new LinkedList<>();

        @Inject
        public Builder(DifferentialExperimentsCache experimentsCache) {

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

            DifferentialExperiment experiment = experimentsCache.getExperiment(experimentAccession);

            List<String> columnHeaders = Arrays.asList(ArrayUtils.remove(tsvFileHeaders, GENE_ID_COLUMN));

            for (String columnHeader : columnHeaders) {
                if (columnHeader.endsWith(".p-value")){
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
}
