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
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.model.Expression;
import uk.ac.ebi.atlas.model.FactorValue;
import uk.ac.ebi.atlas.model.GeneProfile;
import uk.ac.ebi.atlas.model.caches.ExperimentsCache;
import uk.ac.ebi.atlas.streams.GeneProfilesInputStream;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.*;

import static com.google.common.base.Preconditions.checkState;

//ToDo: to be continued in order to handle the includeGenesExpressedAlsoOnNonSelectedFactorValue parameter...
@Named("barChartTrader")
@Scope("prototype")
public class BarChartTrader {

    public static final int AVERAGE_GENES_IN_EXPERIMENT = 45000;

    //key: scaled cutoff
    //value: Map<organism part, gene expression bitset index>
    private NavigableMap<Double, Map<String, BitSet>> geneExpressionIndexes = new TreeMap<>();

    protected BarChartTrader() {
    }

    protected BarChartTrader(NavigableMap<Double, Map<String, BitSet>> geneExpressionIndexes) {
        this.geneExpressionIndexes = geneExpressionIndexes;
    }

    public NavigableMap<Double, Integer> getChart() {

        return getChart(null, false);
    }

    public NavigableMap<Double, Integer> getChart(Set<String> selectedFactorValues, boolean includeGenesExpressedAlsoOnNonSelectedFactorValue) {

        NavigableMap<Double, Integer> barChartPoints = new TreeMap<>();

        for (Double scaledCutoff : geneExpressionIndexes.navigableKeySet()) {

            barChartPoints.put(scaledCutoff, countGenesAboveCutoff(geneExpressionIndexes.get(scaledCutoff), selectedFactorValues, includeGenesExpressedAlsoOnNonSelectedFactorValue));

        }

        return barChartPoints;
    }

    protected static int countGenesAboveCutoff(Map<String, BitSet> geneBitSets, Set<String> selectedFactorValues, boolean includeGenesExpressedAlsoOnNonSelectedFactorValue) {
        BitSet expressedGenesBitSet = new BitSet(AVERAGE_GENES_IN_EXPERIMENT);
        BitSet notExpressedGenesBitSet = new BitSet(AVERAGE_GENES_IN_EXPERIMENT);

        //ToDo:... this should work, but now that we also added the includeGenesExpressedAlsoOnNonSelectedFactorValue parameter it is harder to read...
        for (String organismPart : geneBitSets.keySet()) {
            if (CollectionUtils.isEmpty(selectedFactorValues) || selectedFactorValues.contains(organismPart)) {
                //add
                expressedGenesBitSet.or(geneBitSets.get(organismPart));
            } else if (!includeGenesExpressedAlsoOnNonSelectedFactorValue) {
                notExpressedGenesBitSet.or(geneBitSets.get(organismPart));
            }
        }
        expressedGenesBitSet.andNot(notExpressedGenesBitSet);
        return expressedGenesBitSet.cardinality();
    }


    @Named("barchartTraderBuilder")
    @Scope("prototype")
    public static class Builder {

        private static final Logger logger = Logger.getLogger(Builder.class);

        private NavigableMap<Double, Map<String, BitSet>> geneExpressionIndexes = new TreeMap<>();

        private ExperimentsCache experimentsCache;

        private CutoffScale cutoffScale;

        private GeneProfilesInputStream.Builder geneProfilesInputStreamBuilder;


        //ToDo: we should keep either factorValueValues or factorValues and use FactorValues values or object as keys
        // for the geneExpressionIndexes map
        private Set<String> factorValueValues;
        private SortedSet<FactorValue> defaultFactorValues;


        @Inject
        public Builder(ExperimentsCache experimentsCache, GeneProfilesInputStream.Builder geneProfilesInputStreamBuilder, CutoffScale cutoffScale) {
            this.experimentsCache = experimentsCache;
            this.cutoffScale = cutoffScale;
            this.geneProfilesInputStreamBuilder = geneProfilesInputStreamBuilder;
        }

        public Builder forExperiment(String experimentAccession) {


            Experiment experiment = experimentsCache.getExperiment(experimentAccession);

            defaultFactorValues = experiment.getFactorValues(null);

            // TODO: the static method will be moved to a FactorValues class
            factorValueValues = FactorValue.getFactorValuesStrings(defaultFactorValues);


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

                    Map<String, BitSet> geneBitSets = geneExpressionIndexes.get(cutoff);

                    if (geneBitSets == null) {

                        geneBitSets = initGenesBitSets();
                        geneExpressionIndexes.put(cutoff, geneBitSets);

                    }

                    for (FactorValue defaultFactorValue : defaultFactorValues) {
                        if (expression.isForFactorValue(defaultFactorValue)) {
                            geneBitSets.get(defaultFactorValue.getValue()).set(geneIndexPosition);
                        }
                    }

                }
            }

        }

        protected Map<String, BitSet> initGenesBitSets() {

            Map<String, BitSet> geneBitSets = new HashMap<>();

            for (String factorValue : factorValueValues) {
                geneBitSets.put(factorValue, new BitSet(AVERAGE_GENES_IN_EXPERIMENT));
            }

            return geneBitSets;
        }


        public BarChartTrader create() {
            checkState(factorValueValues != null, "Did you set the experimentAccession ?");
            trimIndexes();
            return new BarChartTrader(geneExpressionIndexes);
        }


        //ToDo:... Huston... we (may) have a problem... will the max cutoff be a lot higher now that we include also genes expressed on non selected factor values?
        protected void trimIndexes() {

            Set<Double> doubles = Sets.newHashSet(geneExpressionIndexes.keySet());
            for (Double scaledCutoff : doubles) {

                if (countGenesAboveCutoff(geneExpressionIndexes.get(scaledCutoff), null, true) < 50) {
                    geneExpressionIndexes.remove(scaledCutoff);
                }

            }

        }

        protected void setFactorValueValues(Set<String> factorValueValues) {
            this.factorValueValues = factorValueValues;
        }

        protected NavigableMap<Double, Map<String, BitSet>> getGeneExpressionIndexes() {
            return geneExpressionIndexes;
        }

        protected void setDefaultFactorValues(SortedSet<FactorValue> defaultFactorValues) {
            this.defaultFactorValues = defaultFactorValues;
        }
    }

}
