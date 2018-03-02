package uk.ac.ebi.atlas.experimentimport.analytics.baseline;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class BaselineAnalytics {
    public static BaselineAnalytics create(String geneId, String assayGroupId, double expressionLevel, double expressionLevelFpkm) {
        return new AutoValue_BaselineAnalytics(geneId, assayGroupId, expressionLevel, expressionLevelFpkm);
    }

    public abstract String geneId();
    public abstract String assayGroupId();
    public abstract double expressionLevel();
    public abstract double expressionLevelFpkm();
}
