package uk.ac.ebi.atlas.dao.dto;

import com.google.common.base.Objects;

public class BaselineExpressionDto {

    private String geneId;

    private String assayGroupId;

    private Double expressionLevel;

    public BaselineExpressionDto(String geneId, String assayGroupId, Double expressionLevel) {
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

        return assayGroupId.equals(that.assayGroupId) && expressionLevel.equals(that.expressionLevel) && geneId.equals(that.geneId);

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
