package uk.ac.ebi.atlas.experimentimport.analytics.baseline;

import com.google.common.base.Objects;
import com.google.common.base.Optional;
import uk.ac.ebi.atlas.model.baseline.Quartiles;

/*
 * Used for loading baseline expressions from the TSV file into the database
 */
public class BaselineAnalytics {

    private String geneId;

    private String assayGroupId;

    private double expressionLevel;
    private double[] quartiles;


    public BaselineAnalytics(String geneId, String assayGroupId, double expressionLevel) {
        this(geneId, assayGroupId, expressionLevel, new double[]{});
    }


    public BaselineAnalytics(String geneId, String assayGroupId, double expressionLevel, double[] quartiles) {
        this.geneId = geneId;
        this.assayGroupId = assayGroupId;
        this.expressionLevel = expressionLevel;
        this.quartiles = quartiles;
    }


    public String getGeneId() {
        return geneId;
    }


    public String getAssayGroupId() {
        return assayGroupId;
    }


    public Double getExpressionLevel() {
        return expressionLevel;
    }


    public double[] getQuartiles() {
        return quartiles;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BaselineAnalytics that = (BaselineAnalytics) o;

        return Double.compare(that.expressionLevel, expressionLevel) == 0 && assayGroupId.equals(that.assayGroupId) && geneId.equals(that.geneId);

    }


    @Override
    public int hashCode() {
        return Objects.hashCode(geneId, assayGroupId, expressionLevel);
    }


    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("geneId", geneId)
                .add("assayGroupId", assayGroupId)
                .add("expressionLevel", expressionLevel)
                .toString();
    }

}
