package uk.ac.ebi.atlas.search.analyticsindex.solr;

import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.web.GeneQuery;

import javax.inject.Named;

import static uk.ac.ebi.atlas.utils.StringUtil.quote;

/**
 * Created by Alfonso Muñoz-Pomer Fuentes <amunoz@ebi.ac.uk> on 19/10/15.
 */
@Named
@Scope("prototype")
public class AnalyticsQueryBuilder {
    private static final String BASELINE_ABOVE_CUTOFF =
            "(experimentType:rnaseq_mrna_baseline AND expressionLevel:[0.5 TO *]) " +
            "OR (experimentType:proteomics_baseline AND expressionLevel:[0 TO *])";
    private static final String DIFFERENTIAL_ABOVE_CUTOFF =
            "(experimentType:(rnaseq_mrna_differential OR microarray_1colour_mrna_differential OR microarray_2colour_mrna_differential OR microarray_1colour_microrna_differential) " +
            "AND foldChange:([* TO -1.0] OR [1.0 TO *]))";

    private static final String BIOENTITY_IDENTIFIER_FIELD = "bioentityIdentifier";
    private static final String IDENTIFIER_SEARCH_FIELD = "identifierSearch";

    private StringBuilder queryStringBuilder = new StringBuilder();
    private SolrQuery solrQuery = new SolrQuery();

    public AnalyticsQueryBuilder facetByExperimentType() {
        solrQuery.setFacet(true);
        solrQuery.setFacetMinCount(1);
        solrQuery.addFacetField("experimentType");
        return this;
    }

    public AnalyticsQueryBuilder setRows(int rows) {
        solrQuery.setRows(rows);
        return this;
    }

    public AnalyticsQueryBuilder queryIdentifierSearch(GeneQuery geneQuery) {
        queryStringBuilder = new StringBuilder(IDENTIFIER_SEARCH_FIELD + ":(");
        if (geneQuery.terms().size() > 0) {
            for (int i = 0 ; i < geneQuery.terms().size() - 1 ; i++) {
                queryStringBuilder.append(quote(geneQuery.terms().get(i))).append(" OR ");
            }
            queryStringBuilder.append(quote(geneQuery.terms().get(geneQuery.terms().size() - 1)));
        }
        queryStringBuilder.append(")");
        return this;
    }

    public AnalyticsQueryBuilder queryBioentityIdentifier(String identifier) {
        queryStringBuilder = new StringBuilder(BIOENTITY_IDENTIFIER_FIELD + ":");
        queryStringBuilder.append(quote(identifier));

        return this;
    }


    public AnalyticsQueryBuilder filterAboveDefaultCutoff() {
        solrQuery.addFilterQuery(BASELINE_ABOVE_CUTOFF + " OR " + DIFFERENTIAL_ABOVE_CUTOFF);
        return this;
    }


    public SolrQuery build() {
        solrQuery.setQuery(queryStringBuilder.toString());
        return solrQuery;
    }

}
