package uk.ac.ebi.atlas.experimentimport.analytics.singlecell;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class SingleCellAnalytics {
    private static final String CSV_LINE_FORMAT = "%s,%s,%f";

    static SingleCellAnalytics create(String geneId, String cellId, double expressionLevel) {
        return new AutoValue_SingleCellAnalytics(geneId, cellId, expressionLevel);
    }

    public abstract String geneId();
    public abstract String cellId();
    public abstract double expressionLevel();

    public String toCsvLine() {
        return String.format(CSV_LINE_FORMAT, geneId(), cellId(), expressionLevel());
    }
}
