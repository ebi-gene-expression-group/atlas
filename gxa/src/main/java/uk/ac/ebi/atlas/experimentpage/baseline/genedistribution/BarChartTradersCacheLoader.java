package uk.ac.ebi.atlas.experimentpage.baseline.genedistribution;

import com.google.common.cache.CacheLoader;
import org.apache.commons.lang3.tuple.Pair;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExpression;
import uk.ac.ebi.atlas.model.experiment.baseline.FactorGroup;
import uk.ac.ebi.atlas.trader.ExperimentTrader;

import javax.annotation.ParametersAreNonnullByDefault;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.*;

@Named
public class BarChartTradersCacheLoader extends CacheLoader<Pair<String,String>, BarChartTrader> {

    private final CutoffScale cutoffScale;

    private final BaselineExpressionsInputStreamFactory inputStreamFactory;

    private final ExperimentTrader experimentTrader;

    @Inject
    public BarChartTradersCacheLoader(ExperimentTrader experimentTrader, BaselineExpressionsInputStreamFactory inputStreamFactory) {
        this(experimentTrader, inputStreamFactory, new CutoffScale());
    }

    BarChartTradersCacheLoader(ExperimentTrader experimentTrader, BaselineExpressionsInputStreamFactory
            inputStreamFactory, CutoffScale
            cutoffScale){
        this.experimentTrader = experimentTrader;
        this.inputStreamFactory = inputStreamFactory;
        this.cutoffScale = cutoffScale;

    }


    @Override
    @ParametersAreNonnullByDefault
    public BarChartTrader load(Pair<String,String> experimentAccessionAndAccessKey) {


        try {
            BarChartTrader barChartTrader = new BarChartTrader(
                    populateGeneExpressionIndexes(
                            inputStreamFactory.createGeneExpressionsInputStream((BaselineExperiment) experimentTrader
                                    .getExperiment
                                    (experimentAccessionAndAccessKey.getLeft(), experimentAccessionAndAccessKey.getRight()))
                    )
            );
            barChartTrader.trimIndexes();
            return barChartTrader;

        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    NavigableMap<Double, Map<FactorGroup, BitSet>>populateGeneExpressionIndexes(ObjectInputStream<BaselineExpressions> inputStream) {
        NavigableMap<Double, Map<FactorGroup, BitSet>> factorGroupGeneExpressionIndexes = new TreeMap<>();
        int geneIndexPosition = 0;

        BaselineExpressions baselineExpressions;

        while ((baselineExpressions = inputStream.readNext()) != null) {
            addGeneToIndexes(baselineExpressions, geneIndexPosition++, factorGroupGeneExpressionIndexes);
        }
        return factorGroupGeneExpressionIndexes;
    }

    protected void addGeneToIndexes(BaselineExpressions baselineExpressions, int geneIndexPosition, NavigableMap<Double, Map<FactorGroup, BitSet>> factorGroupGeneExpressionIndexes) {

        for (BaselineExpression expression : baselineExpressions) {

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
