package uk.ac.ebi.atlas.search.baseline;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class BaselineExperimentExpression {

    public static BaselineExperimentExpression create(String experimentAccession, String assayGroupId, double expressionLevel) {
        return new AutoValue_BaselineExperimentExpression(experimentAccession, assayGroupId, expressionLevel);
    }

    public abstract String experimentAccession();

    public abstract String assayGroupId();

    public abstract double expressionLevel();
}
