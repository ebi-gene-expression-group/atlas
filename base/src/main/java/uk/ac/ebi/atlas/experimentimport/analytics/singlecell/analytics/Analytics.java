package uk.ac.ebi.atlas.experimentimport.analytics.singlecell.analytics;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class Analytics {
    public static Analytics create(String geneId, String cellId, double expressionLevel) {
        return new AutoValue_Analytics(geneId, cellId, expressionLevel);
    }

    public abstract String geneId();
    public abstract String cellId();
    public abstract double expressionLevel();
}
