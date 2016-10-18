package uk.ac.ebi.atlas.web;

import uk.ac.ebi.atlas.profiles.baseline.BaselineExpressionLevelRounder;
import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.search.SemanticQueryTerm;

public class BaselineRequestPreferences extends ExperimentPageRequestPreferences{

    public static final double DEFAULT_CUTOFF = 0.5d;

    private static final String DEFAULT_GENE_QUERY_VALUE = "protein_coding";
    private static final String DEFAULT_GENE_QUERY_CATEGORY = "gene_biotype";
    private double thresholdForPremium;
    private double fractionForPremium;

    @Override
    protected SemanticQuery getDefaultGeneQuery() {
        return SemanticQuery.create(SemanticQueryTerm.create(DEFAULT_GENE_QUERY_VALUE, DEFAULT_GENE_QUERY_CATEGORY));
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

    public double getThresholdForPremium() {
        return thresholdForPremium;
    }

    public double getFractionForPremium() {
        return fractionForPremium;
    }

    public void setThresholdForPremium(double thresholdForPremium){
        this.thresholdForPremium = thresholdForPremium;
    }

    public void setFractionForPremium(double fractionForPremium){
        this.fractionForPremium = fractionForPremium;
    }

}
