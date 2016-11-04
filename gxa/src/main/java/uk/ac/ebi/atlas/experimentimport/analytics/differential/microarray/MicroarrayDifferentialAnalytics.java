package uk.ac.ebi.atlas.experimentimport.analytics.differential.microarray;

import uk.ac.ebi.atlas.experimentimport.analytics.differential.DifferentialAnalytics;
import com.google.common.base.Objects;

public class MicroarrayDifferentialAnalytics implements DifferentialAnalytics {

    private String geneId;

    private String designElement;

    private String contrastId;

    private double pValue;

    private double foldChange;

    private double tstatistic;

    public MicroarrayDifferentialAnalytics(String geneId, String designElement, String contrastId,
                                           double pValue, double foldChange, double tstatistic) {
        this.geneId = geneId;
        this.designElement = designElement;
        this.contrastId = contrastId;
        this.pValue = pValue;
        this.foldChange = foldChange;
        this.tstatistic = tstatistic;
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
        return tstatistic;
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
                Double.compare(that.tstatistic, tstatistic) == 0 &&
                contrastId.equals(that.contrastId) &&
                designElement.equals(that.designElement);

    }

    @Override
    public int hashCode() {
        return Objects.hashCode(designElement, contrastId, pValue, foldChange, tstatistic);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("designElement", designElement)
                .add("contrastId", contrastId)
                .add("pValue", pValue)
                .add("foldChange", foldChange)
                .add("tstatistic", tstatistic)
                .toString();
    }
}
