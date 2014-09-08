package uk.ac.ebi.atlas.search.baseline;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class RnaSeqBslnExpression {

    public static RnaSeqBslnExpression create(String experimentAccession, String assayGroupId, double expressionLevel) {
        return new AutoValue_RnaSeqBslnExpression(experimentAccession, assayGroupId, expressionLevel);
    }

    public abstract String experimentAccession();

    public abstract String assayGroupId();

    public abstract double expressionLevel();
}
