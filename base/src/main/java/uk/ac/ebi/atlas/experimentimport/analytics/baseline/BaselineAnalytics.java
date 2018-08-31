package uk.ac.ebi.atlas.experimentimport.analytics.baseline;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class BaselineAnalytics {
    public abstract String geneId();
    public abstract String assayGroupId();
    public abstract double expressionLevel();
    public abstract double expressionLevelFpkm();
    @SuppressWarnings("mutable")
    public abstract double[] expressionLevels();
    @SuppressWarnings("mutable")
    public abstract double[] expressionLevelsFpkm();

    public static BaselineAnalytics create(String geneId,
                                            String assayGroupId,
                                            double expressionLevel,
                                            double expressionLevelFpkm,
                                            double[] expressionLevels,
                                            double[] expressionLevelsFpkm) {
        return new AutoValue_BaselineAnalytics(
                geneId, assayGroupId, expressionLevel, expressionLevelFpkm, expressionLevels, expressionLevelsFpkm);
    }

    public static BaselineAnalytics create(String geneId,
                                           String assayGroupId,
                                           double expressionLevel,
                                           double expressionLevelFpkm) {
        return new AutoValue_BaselineAnalytics(
                geneId, assayGroupId, expressionLevel, expressionLevelFpkm, new double[0], new double[0]);
    }
}
