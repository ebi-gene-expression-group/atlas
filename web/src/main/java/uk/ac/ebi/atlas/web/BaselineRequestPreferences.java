
package uk.ac.ebi.atlas.web;

import uk.ac.ebi.atlas.profiles.baseline.BaselineExpressionLevelRounder;


public class BaselineRequestPreferences extends ExperimentPageRequestPreferences{

    public static final double DEFAULT_CUTOFF = 0.5d;
    private static final String DEFAULT_GENE_QUERY = "protein_coding";

    private static final String DEFAULT_GENE_QUERY_VALUE = "protein_coding";
    private static final String DEFAULT_GENE_QUERY_SOURCE = "gene_biotype";

    @Override
    protected GeneQuery getDefaultGeneQuery() {
        return GeneQuery.create(DEFAULT_GENE_QUERY);
    }

    @Override
    protected SemanticQuery getDefaultSemanticQuery() {
        return new SemanticQuery(DEFAULT_GENE_QUERY_VALUE, DEFAULT_GENE_QUERY_SOURCE);
    }

    @Override
    public double getDefaultCutoff() {
        return DEFAULT_CUTOFF;
    }

    @Override
    public void setCutoff(Double cutoff) {
        if (cutoff != null) {
            super.setCutoff(BaselineExpressionLevelRounder.round(cutoff));
        } else {
            super.setCutoff(null);
        }
    }

}
