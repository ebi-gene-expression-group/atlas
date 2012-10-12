package uk.ac.ebi.atlas.model;

import java.util.Objects;
import java.util.Set;

import static com.google.common.base.Objects.toStringHelper;
import static com.google.common.base.Preconditions.checkNotNull;

public class GeneExpression implements Comparable<GeneExpression> {

    private String geneId;

    private Expression expression;

    private Integer specificity;


    public GeneExpression(String geneId, double expressionLevel, ExperimentRun experimentRun) {
        this.geneId = checkNotNull(geneId);
        this.expression = new Expression(checkNotNull(experimentRun), expressionLevel);
    }

    public GeneExpression(String geneId, Expression expression, Integer specificity) {
        this.geneId = geneId;
        this.expression = expression;
        this.specificity = specificity;
    }

    public String getGeneId() {
        return geneId;
    }

    public String getRunAccession() {
        return expression.getRunAccession();
    }

    public Set<FactorValue> getFactorValues() {
        return expression.getFactorValues();
    }

    public double getLevel() {
        return expression.getLevel();
    }

    public String getOrganismPart() {
        return expression.getOrganismPart();
    }

    public GeneExpression addFactorValue(String factor, String value) {
        expression.addFactorValue(factor, value);

        return this;
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
    }

    public Integer getSpecificity() {
        return specificity;
    }

    public void setSpecificity(Integer specificity) {
        this.specificity = specificity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(geneId, expression.getExperimentRun(), expression.getLevel());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final GeneExpression other = (GeneExpression) obj;

        return Objects.equals(this.getGeneId(), other.getGeneId())
                && Objects.equals(this.expression.getExperimentRun(), other.expression.getExperimentRun())
                && Objects.equals(this.expression.getLevel(), other.expression.getLevel());
    }

    @Override
    public String toString() {
        return toStringHelper(this)
                .add("geneId", getGeneId())
                .add("level", expression.getLevel())
                .add("experimentRun", expression.getExperimentRun()).toString();
    }

    @Override
    public int compareTo(GeneExpression other) {
        int compareTo = Double.compare(expression.getLevel(), other.expression.getLevel());
        if (compareTo != 0) {
            return compareTo;
        }
        compareTo = getGeneId().compareTo(other.getGeneId());
        if (compareTo != 0) {
            return compareTo;
        }
        return expression.getExperimentRun().compareTo(other.expression.getExperimentRun());

    }
}
