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

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import sun.rmi.server.InactiveGroupException;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.model.Expression;
import uk.ac.ebi.atlas.model.GeneProfile;
import uk.ac.ebi.atlas.model.caches.ExperimentsCache;
import uk.ac.ebi.atlas.streams.GeneProfilesInputStream;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

@Named("barChartTrader")
@Scope("prototype")
public class BarChartTrader implements BarChartGenerator{

    public static final int AVERAGE_GENES_IN_EXPERIMENT = 45000;

    //key: scaled cutoff
    //value: Map<organism part, gene expression bitset index>
    private NavigableMap<Double, Map<String, BitSet>> geneExpressionIndexes = new TreeMap<>();

    @Inject
    protected BarChartTrader() {
    }

    public SortedMap<Double, Integer> getChart() {

        return getChart(null);
    }

    public SortedMap<Double, Integer> getChart(Set<String> selectedOrganismParts) {

        SortedMap<Double, Integer> barChartPoints = new TreeMap<>();

        for (Double scaledCutoff : geneExpressionIndexes.navigableKeySet()) {

            barChartPoints.put(scaledCutoff, countGenesAboveCutoff(geneExpressionIndexes.get(scaledCutoff), selectedOrganismParts));

        }

        return barChartPoints;
    }


    protected int countGenesAboveCutoff(Map<String,BitSet> geneBitSets, Set<String> selectedOrganismParts) {
        BitSet expressedGenesBitSet = new BitSet(AVERAGE_GENES_IN_EXPERIMENT);
        BitSet notExpressedGenesBitSet = new BitSet(AVERAGE_GENES_IN_EXPERIMENT);

        for (String organismPart : geneBitSets.keySet()){
            if (CollectionUtils.isEmpty(selectedOrganismParts) || selectedOrganismParts.contains(organismPart)) {
                //add
                expressedGenesBitSet.or(geneBitSets.get(organismPart));
            } else {
                notExpressedGenesBitSet.or(geneBitSets.get(organismPart));
            }
        }
        expressedGenesBitSet.andNot(notExpressedGenesBitSet);
        return expressedGenesBitSet.cardinality();
    }


    @Named("barChartTraderBuilder")
    @Scope("prototype")
    public static class Builder {

        private static final Logger logger = Logger.getLogger(Builder.class);

        private BarChartTrader barChartTrader = new BarChartTrader();

        private ExperimentsCache experimentsCache;

        private CutoffScale cutoffScale;

        private GeneProfilesInputStream.Builder geneProfilesInputStreamBuilder;

        private Set<String> organismParts;

        @Inject
        public Builder(ExperimentsCache experimentsCache, GeneProfilesInputStream.Builder geneProfilesInputStreamBuilder, CutoffScale cutoffScale){
            this.experimentsCache = experimentsCache;
            this.cutoffScale = cutoffScale;
            this.geneProfilesInputStreamBuilder = geneProfilesInputStreamBuilder;
        }

        public Builder forExperiment(String experimentAccession) {

            organismParts = experimentsCache.getExperiment(experimentAccession).getAllOrganismParts();

            try (ObjectInputStream<GeneProfile> inputStream = geneProfilesInputStreamBuilder.forExperiment(experimentAccession).create()) {

                int geneIndexPosition = 0;

                GeneProfile geneProfile;

                while((geneProfile = inputStream.readNext()) != null){
                    addGeneToIndexes(geneProfile, geneIndexPosition++);
                }

                return this;
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
                throw new IllegalStateException("IOException when invoking ObjectInputStream.close()");
            }

        }

        protected void addGeneToIndexes(GeneProfile geneProfile, int geneIndexPosition) {

            for (Expression expression : geneProfile){

                SortedSet<Double> cutoffsSmallerThanExpression = cutoffScale.getValuesSmallerThan(expression.getLevel());

                for (Double cutoff: cutoffsSmallerThanExpression){

                    Map<String, BitSet> geneBitSets = barChartTrader.geneExpressionIndexes.get(cutoff);

                    if (geneBitSets == null) {

                        geneBitSets = initGenesBitSets(cutoff);

                    }
                    geneBitSets.get(expression.getOrganismPart()).set(geneIndexPosition);

                }
            }

        }

        protected Map<String, BitSet> initGenesBitSets(Double cutoff) {

            Map<String, BitSet> geneBitSets = new HashMap<>();

            for(String organismPart: organismParts){
                geneBitSets.put(organismPart, new BitSet(AVERAGE_GENES_IN_EXPERIMENT));
            }

            barChartTrader.geneExpressionIndexes.put(cutoff, geneBitSets);

            return geneBitSets;
        }


        public BarChartTrader create() {
            checkState(organismParts != null, "Did you set the experimentAccession ?");

            //ToDo
            //to shrink the index, removing entries related to less then 50 genes... we may:
            //SortedMap<Double, Integer> chartData = barChartTrader.getChart();
            //then we should trim indexes based on the chart...

            return barChartTrader;
        }

    }

}
