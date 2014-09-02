package uk.ac.ebi.atlas.search.baseline;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class RnaSeqBslnExpression {

    public static RnaSeqBslnExpression create(String geneId, String experimentAccession, String assayGroupId, double expressionLevel) {
        return new AutoValue_RnaSeqBslnExpression(geneId, experimentAccession, assayGroupId, expressionLevel);
    }

    public abstract String geneId();

    public abstract String experimentAccession();

    public abstract String assayGroupId();

    public abstract double expressionLevel();
}
