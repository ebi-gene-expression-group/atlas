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

package uk.ac.ebi.atlas.model.barcharts;

import com.google.common.collect.Sets;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.model.Expression;
import uk.ac.ebi.atlas.model.FactorValueSet;
import uk.ac.ebi.atlas.model.GeneExpressionPrecondition;
import uk.ac.ebi.atlas.model.GeneProfile;
import uk.ac.ebi.atlas.streams.GeneProfilesInputStream;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.*;

//ToDo: to be continued in order to handle the includeGenesExpressedAlsoOnNonSelectedFactorValue parameter...
@Named("barChartTrader")
@Scope("prototype")
public class BarChartTrader {

    public static final int AVERAGE_GENES_IN_EXPERIMENT = 45000;

    //key: scaled cutoff
    //value: Map<organism part, gene expression bitset index>
//    private NavigableMap<Double, Map<String, BitSet>> geneExpressionIndexes = new TreeMap<>();

    private NavigableMap<Double, Map<FactorValueSet, BitSet>> factorSetGeneExpressionIndexes = new TreeMap<>();

    protected BarChartTrader() {
    }

//    protected BarChartTrader(NavigableMap<Double, Map<String, BitSet>> geneExpressionIndexes) {
//        this.geneExpressionIndexes = geneExpressionIndexes;
//    }

    public BarChartTrader(NavigableMap<Double, Map<FactorValueSet, BitSet>> factorSetGeneExpressionIndexes) {
        this.factorSetGeneExpressionIndexes = factorSetGeneExpressionIndexes;
    }


//    public NavigableMap<Double, Integer> getChart(Set<String> selectedFactorValues, boolean includeGenesExpressedAlsoOnNonSelectedFactorValue) {
//
//        NavigableMap<Double, Integer> barChartPoints = new TreeMap<>();
//
//        for (Double scaledCutoff : geneExpressionIndexes.navigableKeySet()) {
//
//            barChartPoints.put(scaledCutoff, countGenesAboveCutoff(geneExpressionIndexes.get(scaledCutoff), selectedFactorValues, includeGenesExpressedAlsoOnNonSelectedFactorValue));
//
//        }
//
//        return barChartPoints;
//    }

    public NavigableMap<Double, Integer> getChart(FactorValueSet limitingFactorValueSet, FactorValueSet selectedFactorValues) {

        NavigableMap<Double, Integer> barChartPoints = new TreeMap<>();

        for (Double scaledCutoff : factorSetGeneExpressionIndexes.navigableKeySet()) {

            barChartPoints.put(scaledCutoff, countGenesAboveCutoff(factorSetGeneExpressionIndexes.get(scaledCutoff), limitingFactorValueSet, selectedFactorValues));

        }

        return barChartPoints;
    }


//    protected static int countGenesAboveCutoff(Map<String, BitSet> geneBitSets, Set<String> selectedFactorValues, boolean includeGenesExpressedAlsoOnNonSelectedFactorValue) {
//        BitSet expressedGenesBitSet = new BitSet(AVERAGE_GENES_IN_EXPERIMENT);
//        BitSet notExpressedGenesBitSet = new BitSet(AVERAGE_GENES_IN_EXPERIMENT);
//
//        //ToDo:... this should work, but now that we also added the includeGenesExpressedAlsoOnNonSelectedFactorValue parameter it is harder to read...
//        for (String organismPart : geneBitSets.keySet()) {
//            if (CollectionUtils.isEmpty(selectedFactorValues) || selectedFactorValues.contains(organismPart)) {
//                //add
//                expressedGenesBitSet.or(geneBitSets.get(organismPart));
//            } else if (!includeGenesExpressedAlsoOnNonSelectedFactorValue) {
//                notExpressedGenesBitSet.or(geneBitSets.get(organismPart));
//            }
//        }
//        expressedGenesBitSet.andNot(notExpressedGenesBitSet);
//        return expressedGenesBitSet.cardinality();
//    }

    protected static int countGenesAboveCutoff(Map<FactorValueSet, BitSet> geneBitSets, FactorValueSet limitingFactorValueSet, FactorValueSet selectedFactorValues) {
        BitSet expressedGenesBitSet = new BitSet(AVERAGE_GENES_IN_EXPERIMENT);

        for (FactorValueSet setFactorValues : geneBitSets.keySet()) {
            if (forLimitingFactorValues(setFactorValues, limitingFactorValueSet) && forQueryFactorValues(setFactorValues, selectedFactorValues)) {
                //add
                expressedGenesBitSet.or(geneBitSets.get(setFactorValues));
            }
        }
        return expressedGenesBitSet.cardinality();
    }


    protected static boolean forLimitingFactorValues(FactorValueSet factorValueSet, FactorValueSet limitingFactorValueSet) {
        return CollectionUtils.isEmpty(limitingFactorValueSet) || factorValueSet.containsAll(limitingFactorValueSet);
    }

    protected static boolean forQueryFactorValues(FactorValueSet factorValueSet, FactorValueSet queryFactorValues) {
        return CollectionUtils.isEmpty(queryFactorValues) || factorValueSet.contains(queryFactorValues);
    }


    @Named("barchartTraderBuilder")
    @Scope("prototype")
    public static class Builder {

        private static final Logger logger = Logger.getLogger(Builder.class);

        private NavigableMap<Double, Map<FactorValueSet, BitSet>> factorSetGeneExpressionIndexes = new TreeMap<>();

        private CutoffScale cutoffScale;

        private GeneProfilesInputStream.Builder geneProfilesInputStreamBuilder;


        @Inject
        public Builder(GeneProfilesInputStream.Builder geneProfilesInputStreamBuilder
                , CutoffScale cutoffScale, GeneExpressionPrecondition geneExpressionPrecondition) {
            this.cutoffScale = cutoffScale;
            this.geneProfilesInputStreamBuilder = geneProfilesInputStreamBuilder;
            geneExpressionPrecondition.setCutoff(0d);
        }

        public Builder forExperiment(String experimentAccession) {

            try (ObjectInputStream<GeneProfile> inputStream = geneProfilesInputStreamBuilder.forExperiment(experimentAccession).create()) {

                populateGeneExpressionIndexes(inputStream);

                return this;
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
                throw new IllegalStateException("IOException when invoking ObjectInputStream.close()");
            }

        }

        protected void populateGeneExpressionIndexes(ObjectInputStream<GeneProfile> inputStream) {
            int geneIndexPosition = 0;

            GeneProfile geneProfile;

            while ((geneProfile = inputStream.readNext()) != null) {
                addGeneToIndexes(geneProfile, geneIndexPosition++);
            }
        }

        protected void addGeneToIndexes(GeneProfile geneProfile, int geneIndexPosition) {

            for (Expression expression : geneProfile) {

                SortedSet<Double> cutoffsSmallerThanExpression = cutoffScale.getValuesSmallerThan(expression.getLevel());

                for (Double cutoff : cutoffsSmallerThanExpression) {

                    Map<FactorValueSet, BitSet> geneBitSets = factorSetGeneExpressionIndexes.get(cutoff);

                    if (geneBitSets == null) {
                        geneBitSets = new HashMap<>();
                        factorSetGeneExpressionIndexes.put(cutoff, geneBitSets);

                    }

                    FactorValueSet factorValueSet = expression.getFactorValueSet();
                    BitSet bitSet = geneBitSets.get(factorValueSet);
                    if (bitSet == null) {
                        bitSet = new BitSet(AVERAGE_GENES_IN_EXPERIMENT);
                        geneBitSets.put(factorValueSet, bitSet);
                    }

                    bitSet.set(geneIndexPosition);

                }
            }

        }

        public BarChartTrader create() {
            trimIndexes();
            return new BarChartTrader(factorSetGeneExpressionIndexes);
        }


        //ToDo:... Huston... we (may) have a problem... will the max cutoff be a lot higher now that we include also genes expressed on non selected factor values?
        protected void trimIndexes() {

            Set<Double> doubles = Sets.newHashSet(factorSetGeneExpressionIndexes.keySet());
            for (Double scaledCutoff : doubles) {

                if (countGenesAboveCutoff(factorSetGeneExpressionIndexes.get(scaledCutoff), limitingFactorValueSet, null) < 50) {
                    factorSetGeneExpressionIndexes.remove(scaledCutoff);
                }

            }

        }

        protected NavigableMap<Double, Map<FactorValueSet, BitSet>> getGeneExpressionIndexes() {
            return factorSetGeneExpressionIndexes;
        }

    }

}
