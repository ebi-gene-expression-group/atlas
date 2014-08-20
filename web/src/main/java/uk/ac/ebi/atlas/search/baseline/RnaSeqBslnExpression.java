package uk.ac.ebi.atlas.search.baseline;

import com.google.common.base.Objects;

public class RnaSeqBslnExpression {

    private String geneId;

    private String experimentAccession;

    private String assayGroupId;

    private double expressionLevel;

    public RnaSeqBslnExpression(String geneId, String experimentAccession, String assayGroupId, double expressionLevel) {
        this.geneId = geneId;
        this.experimentAccession = experimentAccession;
        this.assayGroupId = assayGroupId;
        this.expressionLevel = expressionLevel;
    }

    public String getGeneId() {
        return geneId;
    }

    public String getExperimentAccession() {
        return experimentAccession;
    }

    public String getAssayGroupId() {
        return assayGroupId;
    }

    public double getExpressionLevel() {
        return expressionLevel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RnaSeqBslnExpression that = (RnaSeqBslnExpression) o;

        return Double.compare(that.expressionLevel, expressionLevel) == 0 && assayGroupId.equals(that.assayGroupId) && experimentAccession.equals(that.experimentAccession) && geneId.equals(that.geneId);

    }

    @Override
    public int hashCode() {
        return Objects.hashCode(geneId, experimentAccession, assayGroupId, expressionLevel);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("geneId", geneId)
                .add("experimentAccession", experimentAccession)
                .add("assayGroupId", assayGroupId)
                .add("expressionLevel", expressionLevel)
                .toString();
    }

}
