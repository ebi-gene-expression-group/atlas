package uk.ac.ebi.atlas.model.barcharts;

import com.google.common.collect.Sets;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.model.CompleteGeneProfile;
import uk.ac.ebi.atlas.model.Expression;
import uk.ac.ebi.atlas.model.FactorGroup;
import uk.ac.ebi.atlas.streams.GeneProfileInputStreamBuilder;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.*;

@Named("barchartTraderBuilder")
@Scope("prototype")
public class BitIndexBuilder {

    private static final Logger logger = Logger.getLogger(BitIndexBuilder.class);

    private NavigableMap<Double, Map<FactorGroup, BitSet>> factorGroupGeneExpressionIndexes = new TreeMap<>();

    private CutoffScale cutoffScale;

    private GeneProfileInputStreamBuilder geneProfilesInputStreamBuilder;


    @Inject
    public BitIndexBuilder(GeneProfileInputStreamBuilder geneProfilesInputStreamBuilder
            , CutoffScale cutoffScale) {
        this.cutoffScale = cutoffScale;
        this.geneProfilesInputStreamBuilder = geneProfilesInputStreamBuilder;
    }

    public BitIndexBuilder forExperiment(String experimentAccession) {

        try (ObjectInputStream<CompleteGeneProfile> inputStream =
                     geneProfilesInputStreamBuilder.forExperiment(experimentAccession).createCompleteGeneProfileInputStream()) {

            populateGeneExpressionIndexes(inputStream);

            return this;
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new IllegalStateException("IOException when invoking ObjectInputStream.close()");
        }

    }

    protected void populateGeneExpressionIndexes(ObjectInputStream<CompleteGeneProfile> inputStream) {
        int geneIndexPosition = 0;

        CompleteGeneProfile geneProfile;

        while ((geneProfile = inputStream.readNext()) != null) {
            addGeneToIndexes(geneProfile, geneIndexPosition++);
        }
    }

    protected void addGeneToIndexes(CompleteGeneProfile geneProfile, int geneIndexPosition) {

        for (Expression expression : geneProfile) {

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
        trimIndexes();
        return new BarChartTrader(factorGroupGeneExpressionIndexes);
    }

    protected void trimIndexes() {

        Set<Double> doubles = Sets.newHashSet(factorGroupGeneExpressionIndexes.keySet());
        for (Double scaledCutoff : doubles) {

            if (BarChartTrader.countGenesAboveCutoff(factorGroupGeneExpressionIndexes.get(scaledCutoff), null, null) < 50) {
                factorGroupGeneExpressionIndexes.remove(scaledCutoff);
            }

        }

    }

    protected NavigableMap<Double, Map<FactorGroup, BitSet>> getGeneExpressionIndexes() {
        return factorGroupGeneExpressionIndexes;
    }

}
