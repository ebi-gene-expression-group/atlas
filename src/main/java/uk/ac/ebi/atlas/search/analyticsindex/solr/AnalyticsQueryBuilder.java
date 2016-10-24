package uk.ac.ebi.atlas.search.analyticsindex.solr;

import com.google.common.collect.ImmutableList;
import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.search.SemanticQuery;

import javax.inject.Named;
import java.util.List;

import static uk.ac.ebi.atlas.search.analyticsindex.solr.AnalyticsSolrQuery.Operator.AND;
import static uk.ac.ebi.atlas.search.analyticsindex.solr.AnalyticsSolrQuery.Operator.OR;
import static uk.ac.ebi.atlas.search.analyticsindex.solr.AnalyticsQueryBuilder.Field.CONDITIONS_SEARCH;
import static uk.ac.ebi.atlas.search.analyticsindex.solr.AnalyticsQueryBuilder.Field.FACTOR_TYPE;
import static uk.ac.ebi.atlas.search.analyticsindex.solr.AnalyticsQueryBuilder.Field.IDENTIFIER_SEARCH;
import static uk.ac.ebi.atlas.search.analyticsindex.solr.AnalyticsQueryBuilder.Field.SPECIES;

@Named
@Scope("prototype")
public class AnalyticsQueryBuilder {
    public enum Field {
        EXPERIMENT_TYPE("experimentType"),
        BIOENTITY_IDENTIFIER("bioentityIdentifier"),
        SPECIES("species"),
        IDENTIFIER_SEARCH("identifierSearch"),
        CONDITIONS_SEARCH("conditionsSearch"),
        FACTOR_TYPE("defaultQueryFactorType");
        private final String name;

        Field(String name){
            this.name = name;
        }

        @Override
        public String toString() {
            return this.name;
        }
    }

    // Values below cutoff are excluded in index build, keeping comments just for reference
    // public static final double DEFAULT_BASELINE_CUT_OFF = 0.5;
    // public static final double DEFAULT_PROTEOMICS_CUT_OFF = 0;

    private static final String BASELINE_ABOVE_CUTOFF = "(experimentType:(rnaseq_mrna_baseline OR proteomics_baseline))";
    private static final String DIFFERENTIAL_ABOVE_CUTOFF =
            "(experimentType:(rnaseq_mrna_differential OR microarray_1colour_mrna_differential OR microarray_2colour_mrna_differential OR microarray_1colour_microrna_differential) " +
            "AND pValue:[* TO 0.05])";
    private static final String DEFAULT_QUERY = "*:*";

    private ImmutableList.Builder<AnalyticsSolrQuery> queryClausesBuilder = ImmutableList.builder();

    private SolrQuery solrQuery = new SolrQuery().setFacetLimit(-1);

    private void addQueryClause(Field searchField, SemanticQuery searchValue) {
        if (searchValue.isNotEmpty()) {
            queryClausesBuilder.add(new AnalyticsSolrQuery(searchField.toString(), searchValue));
        }
    }

    public AnalyticsQueryBuilder queryIdentifierOrConditionsSearch(SemanticQuery query){
        queryClausesBuilder.add(new AnalyticsSolrQuery(
                AnalyticsSolrQuery.Operator.OR,
                new AnalyticsSolrQuery(IDENTIFIER_SEARCH.toString(), query),
                new AnalyticsSolrQuery(CONDITIONS_SEARCH.toString(), query)
        ));
        return this;
    }

    public AnalyticsQueryBuilder queryIdentifierSearch(SemanticQuery geneQuery) {
        addQueryClause(IDENTIFIER_SEARCH, geneQuery);
        return this;
    }

    public AnalyticsQueryBuilder queryConditionsSearch(SemanticQuery conditionQuery) {
        addQueryClause(CONDITIONS_SEARCH, conditionQuery);
        return this;
    }

    public AnalyticsQueryBuilder withFactorType(String factorType) {
        addQueryClause(FACTOR_TYPE, SemanticQuery.create(factorType));
        return this;
    }

    public AnalyticsQueryBuilder ofSpecies(String species) {
        addQueryClause(SPECIES, SemanticQuery.create(species));
        return this;
    }

    public AnalyticsQueryBuilder facetBy(Field f){
        solrQuery.setFacet(true);
        solrQuery.setFacetMinCount(1);
        solrQuery.addFacetField(f.toString());
        return this;
    }

    public AnalyticsQueryBuilder setFacetLimit(int facetLimit){
        solrQuery.setFacetLimit(facetLimit);
        return this;
    }


    public AnalyticsQueryBuilder setRows(int rows) {
        solrQuery.setRows(rows);
        return this;
    }

    public AnalyticsQueryBuilder filterAboveDefaultCutoff() {
        solrQuery.addFilterQuery(BASELINE_ABOVE_CUTOFF + " OR " + DIFFERENTIAL_ABOVE_CUTOFF);
        return this;
    }

    public AnalyticsQueryBuilder filterBaselineAboveDefaultCutoff() {
        solrQuery.addFilterQuery(BASELINE_ABOVE_CUTOFF);
        return this;
    }

    public SolrQuery build() {
        List<AnalyticsSolrQuery> queryClauses = queryClausesBuilder.build();

        if (queryClauses.isEmpty()) {
            solrQuery.setQuery(DEFAULT_QUERY);
        } else {
            solrQuery.setQuery(new AnalyticsSolrQuery(AND, queryClauses.toArray(new AnalyticsSolrQuery[0])).toString());
        }

        return solrQuery;
    }
}
