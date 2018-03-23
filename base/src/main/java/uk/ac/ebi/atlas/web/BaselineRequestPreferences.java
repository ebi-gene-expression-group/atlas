package uk.ac.ebi.atlas.web;

import uk.ac.ebi.atlas.model.ExpressionUnit;
import uk.ac.ebi.atlas.solr.BioentityPropertyName;
import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.search.SemanticQueryTerm;

public abstract class BaselineRequestPreferences<U extends ExpressionUnit.Absolute>
        extends ExperimentPageRequestPreferences<U> {
    private static final String DEFAULT_GENE_QUERY_VALUE = "protein_coding";
    private static final String DEFAULT_GENE_QUERY_CATEGORY = BioentityPropertyName.GENE_BIOTYPE.name;

    @Override
    protected SemanticQuery getDefaultGeneQuery() {
        return SemanticQuery.create(SemanticQueryTerm.create(DEFAULT_GENE_QUERY_VALUE, DEFAULT_GENE_QUERY_CATEGORY));
    }
}
