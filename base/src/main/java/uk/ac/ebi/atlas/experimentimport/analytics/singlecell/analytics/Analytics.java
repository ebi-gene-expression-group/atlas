package uk.ac.ebi.atlas.experimentimport.analytics.singlecell.analytics;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class Analytics {
    public abstract String geneId();
    public abstract String cellId();
    public abstract double expressionLevel();

    public static Analytics create(String geneId, String cellId, double expressionLevel) {
        return new AutoValue_Analytics(geneId, cellId, expressionLevel);
    }
}
