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

package uk.ac.ebi.atlas.experimentpage.baseline.genedistribution;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.model.baseline.BaselineExpression;
import uk.ac.ebi.atlas.model.baseline.FactorGroup;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.*;

import static com.google.common.base.Preconditions.checkState;

@Named
@Scope("prototype")
public class BarChartTraderBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(BarChartTraderBuilder.class);

    private NavigableMap<Double, Map<FactorGroup, BitSet>> factorGroupGeneExpressionIndexes = new TreeMap<>();

    private CutoffScale cutoffScale;

    private BaselineExpressionsInputStreamFactory inputStreamFactory;

    @Inject
    public BarChartTraderBuilder(BaselineExpressionsInputStreamFactory inputStreamFactory, CutoffScale cutoffScale) {
        this.cutoffScale = cutoffScale;
        this.inputStreamFactory = inputStreamFactory;
    }

    public BarChartTraderBuilder forExperiment(String experimentAccession) {

        try (ObjectInputStream<BaselineExpressions> inputStream = inputStreamFactory.createGeneExpressionsInputStream(experimentAccession)) {
            populateGeneExpressionIndexes(inputStream);
            return this;
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw new IllegalStateException("IOException when invoking ObjectInputStream.close()");
        }

    }

    void populateGeneExpressionIndexes(ObjectInputStream<BaselineExpressions> inputStream) {
        int geneIndexPosition = 0;

        BaselineExpressions baselineExpressions;

        while ((baselineExpressions = inputStream.readNext()) != null) {
            addGeneToIndexes(baselineExpressions, geneIndexPosition++);
        }
    }

    protected void addGeneToIndexes(BaselineExpressions baselineExpressions, int geneIndexPosition) {

        for (BaselineExpression expression : baselineExpressions) {

            if(expression.isKnown()) {
                SortedSet<Double> cutoffsSmallerThanExpression = cutoffScale.getValuesSmallerThan(expression.getLevel());

                for (Double cutoff : cutoffsSmallerThanExpression) {

                    Map<FactorGroup, BitSet> geneBitSets = factorGroupGeneExpressionIndexes.get(cutoff);

                    if (geneBitSets == null) {
                        geneBitSets = new HashMap<>();
                        factorGroupGeneExpressionIndexes.put(cutoff, geneBitSets);

                    }

                    FactorGroup factorGroup = expression.getFactorGroup();
                    BitSet bitSet = geneBitSets.get(factorGroup);
                    if (bitSet == null) {
                        bitSet = new BitSet(BarChartTrader.AVERAGE_GENES_IN_EXPERIMENT);
                        geneBitSets.put(factorGroup, bitSet);
                    }

                    bitSet.set(geneIndexPosition);

                }
            }
        }

    }

    public BarChartTrader create() {
        checkState(factorGroupGeneExpressionIndexes.size() > 0, "Index is empty, please verify that the forExperiment method was invoked");

        BarChartTrader barChartTrader = new BarChartTrader(factorGroupGeneExpressionIndexes);
        barChartTrader.trimIndexes();
        return barChartTrader;
    }


    protected NavigableMap<Double, Map<FactorGroup, BitSet>> getGeneExpressionIndexes() {
        return factorGroupGeneExpressionIndexes;
    }

}
