package uk.ac.ebi.atlas.web;

import uk.ac.ebi.atlas.model.experiment.baseline.BioentityPropertyName;
import uk.ac.ebi.atlas.profiles.baseline.BaselineExpressionLevelRounder;
import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.search.SemanticQueryTerm;

public abstract class BaselineRequestPreferences extends ExperimentPageRequestPreferences{

    private static final String DEFAULT_GENE_QUERY_VALUE = "protein_coding";
    private static final String DEFAULT_GENE_QUERY_CATEGORY = BioentityPropertyName.GENE_BIOTYPE.name;

    @Override
    protected SemanticQuery getDefaultGeneQuery() {
        return SemanticQuery.create(SemanticQueryTerm.create(DEFAULT_GENE_QUERY_VALUE, DEFAULT_GENE_QUERY_CATEGORY));
    }

    @Override
    public void setCutoff(Double cutoff) {
        if (cutoff != null) {
            super.setCutoff(BaselineExpressionLevelRounder.round(cutoff));
        } else {
            super.setCutoff(null);
        }
    }

    protected static void setRequestAllData(BaselineRequestPreferences preferences){
        preferences.setCutoff(0.0d);
        preferences.setGeneQuery(SemanticQuery.create());
    }
}
