package uk.ac.ebi.atlas.experimentloader.analytics.baseline;

import com.google.common.base.Objects;

/*
 * Used for loading baseline expressions from the TSV file into the database
 */
public class BaselineExpressionDto {

    private String geneId;

    private String assayGroupId;

    private double expressionLevel;

    public BaselineExpressionDto(String geneId, String assayGroupId, double expressionLevel) {
        this.geneId = geneId;
        this.assayGroupId = assayGroupId;
        this.expressionLevel = expressionLevel;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BaselineExpressionDto that = (BaselineExpressionDto) o;

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
