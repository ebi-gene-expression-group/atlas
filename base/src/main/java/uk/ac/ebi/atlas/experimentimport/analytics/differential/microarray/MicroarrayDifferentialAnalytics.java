package uk.ac.ebi.atlas.experimentimport.analytics.differential.microarray;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import uk.ac.ebi.atlas.experimentimport.analytics.differential.DifferentialAnalytics;

public class MicroarrayDifferentialAnalytics implements DifferentialAnalytics {

    private String geneId;

    private String designElement;

    private String contrastId;

    private double pValue;

    private double foldChange;

    private double tStatistic;

    public MicroarrayDifferentialAnalytics(String geneId, String designElement, String contrastId,
                                           double pValue, double foldChange, double tStatistic) {
        this.geneId = geneId;
        this.designElement = designElement;
        this.contrastId = contrastId;
        this.pValue = pValue;
        this.foldChange = foldChange;
        this.tStatistic = tStatistic;
    }

    public String getGeneId() {
        return geneId;
    }

    public String getDesignElement() {
        return designElement;
    }

    public String getContrastId() {
        return contrastId;
    }

    public double getpValue() {
        return pValue;
    }

    public double getFoldChange() {
        return foldChange;
    }

    @Override
    public double getTStatistic() {
        return tStatistic;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MicroarrayDifferentialAnalytics that = (MicroarrayDifferentialAnalytics) o;

        return Double.compare(that.foldChange, foldChange) == 0 &&
                Double.compare(that.pValue, pValue) == 0 &&
                Double.compare(that.tStatistic, tStatistic) == 0 &&
                contrastId.equals(that.contrastId) &&
                designElement.equals(that.designElement);

    }

    @Override
    public int hashCode() {
        return Objects.hashCode(designElement, contrastId, pValue, foldChange, tStatistic);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("designElement", designElement)
                .add("contrastId", contrastId)
                .add("pValue", pValue)
                .add("foldChange", foldChange)
                .add("tStatistic", tStatistic)
                .toString();
    }
}
