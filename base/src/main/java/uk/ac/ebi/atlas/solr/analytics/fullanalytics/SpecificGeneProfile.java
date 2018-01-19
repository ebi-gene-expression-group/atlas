package uk.ac.ebi.atlas.solr.analytics.fullanalytics;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ComparisonChain;

import java.util.Comparator;

@AutoValue
public abstract class SpecificGeneProfile {
    public static final Comparator<SpecificGeneProfile> COMPARATOR_BY_SPECIFICITY_AND_NAIVE_DIFF_EXPRESSION = (o1, o2) ->
            ComparisonChain.start()
                    .compare(
                            o1.countsInSelection() + o1.countsOutsideSelection(),
                            o2.countsInSelection() + o2.countsOutsideSelection())

                    .compare(o1.countsOutsideSelection(), o2.countsOutsideSelection())

                    // Notice that we place o2 before o1 to achieve descending order
                    .compare(
                            o2.avgExpressionInSelection() / (o2.maxExpressionOutsideSelection().equals(0.0) ?
                                    1.0 : o2.maxExpressionOutsideSelection()),
                            o1.avgExpressionInSelection() / (o1.maxExpressionOutsideSelection().equals(0.0) ?
                                    1.0 : o1.maxExpressionOutsideSelection()))

                    .result();

    static SpecificGeneProfile create(String geneId,
                                      Integer countsInSelection,
                                      Double avgExpressionInSelection,
                                      Integer countsOutsideSelection,
                                      Double maxExpressionOutsideSelection) {
        return new AutoValue_SpecificGeneProfile(
                geneId,
                countsInSelection,
                avgExpressionInSelection,
                countsOutsideSelection,
                maxExpressionOutsideSelection);
    }

    abstract String geneId();
    abstract Integer countsInSelection();
    abstract Double avgExpressionInSelection();
    abstract Integer countsOutsideSelection();
    abstract Double maxExpressionOutsideSelection();
}