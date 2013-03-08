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

package uk.ac.ebi.atlas.model.baseline.barcharts;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.model.baseline.BaselineExpression;
import uk.ac.ebi.atlas.model.baseline.FactorGroup;
import uk.ac.ebi.atlas.model.baseline.GeneExpressions;
import uk.ac.ebi.atlas.streams.InputStreamFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.*;

import static com.google.common.base.Preconditions.checkState;

@Named("bitIndexBuilder")
@Scope("prototype")
public class BitIndexBuilder {

    private static final Logger logger = Logger.getLogger(BitIndexBuilder.class);

    private NavigableMap<Double, Map<FactorGroup, BitSet>> factorGroupGeneExpressionIndexes = new TreeMap<>();

    private CutoffScale cutoffScale;

    private InputStreamFactory geneProfilesInputStreamBuilder;


    @Inject
    public BitIndexBuilder(InputStreamFactory geneProfilesInputStreamBuilder
            , CutoffScale cutoffScale) {
        this.cutoffScale = cutoffScale;
        this.geneProfilesInputStreamBuilder = geneProfilesInputStreamBuilder;
    }

    public BitIndexBuilder forExperiment(String experimentAccession) {

        try (ObjectInputStream<GeneExpressions> inputStream =
                     geneProfilesInputStreamBuilder.createGeneExpressionsInputStream(experimentAccession)) {

            populateGeneExpressionIndexes(inputStream);

            return this;
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new IllegalStateException("IOException when invoking ObjectInputStream.close()");
        }

    }

    protected void populateGeneExpressionIndexes(ObjectInputStream<GeneExpressions> inputStream) {
        int geneIndexPosition = 0;

        GeneExpressions geneProfile;

        while ((geneProfile = inputStream.readNext()) != null) {
            addGeneToIndexes(geneProfile, geneIndexPosition++);
        }
    }

    protected void addGeneToIndexes(GeneExpressions geneProfile, int geneIndexPosition) {

        for (BaselineExpression expression : geneProfile) {

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
