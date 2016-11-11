package uk.ac.ebi.atlas.experimentimport.analytics.differential.rnaseq;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import uk.ac.ebi.atlas.experimentimport.analytics.differential.DifferentialAnalytics;

public class RnaSeqDifferentialAnalytics implements DifferentialAnalytics {

    private String geneId;

    private String contrastId;

    private double pValue;

    private double foldChange;

    public RnaSeqDifferentialAnalytics(String geneId, String contrastId,
                                       double pValue, double foldChange) {
        this.geneId = geneId;
        this.contrastId = contrastId;
        this.pValue = pValue;
        this.foldChange = foldChange;
    }

    @Override
    public String getGeneId() {
        return geneId;
    }

    @Override
    public String getContrastId() {
        return contrastId;
    }

    @Override
    public double getpValue() {
        return pValue;
    }

    @Override
    public double getFoldChange() {
        return foldChange;
    }

    @Override
    public double getTStatistic() {
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RnaSeqDifferentialAnalytics that = (RnaSeqDifferentialAnalytics) o;

        return Double.compare(that.foldChange, foldChange) == 0 &&
                Double.compare(that.pValue, pValue) == 0 &&
                contrastId.equals(that.contrastId) &&
                geneId.equals(that.geneId);

    }

    @Override
    public int hashCode() {
        return Objects.hashCode(geneId, contrastId, pValue, foldChange);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("designElement", geneId)
                .add("contrastId", contrastId)
                .add("pValue", pValue)
                .add("foldChange", foldChange)
                .toString();
    }
}
