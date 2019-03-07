package uk.ac.ebi.atlas.markergenes;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class MarkerGene {

    public abstract String geneId();
    public abstract int kWhereMarker();
    public abstract int clusterIdWhereMarker();
    public abstract double pValue();
    public abstract int clusterId();
    public abstract double medianExpression();
    public abstract double meanExpression();

    public static MarkerGene create(String geneId,
                                        int kWhereMarker,
                                        int clusterIdWhereMarker,
                                        double pValue,
                                        int clusterId,
                                        double medianExpression,
                                        double meanExpression) {
        return new AutoValue_MarkerGene(
                geneId,
                kWhereMarker,
                clusterIdWhereMarker,
                pValue,
                clusterId,
                medianExpression,
                meanExpression
        );
    }
}
