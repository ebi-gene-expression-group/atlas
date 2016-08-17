package uk.ac.ebi.atlas.search.analyticsindex.solr;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.search.SemanticQuery;

import javax.inject.Named;

import java.util.ArrayList;

import static org.apache.commons.lang3.StringUtils.wrap;

@Named
@Scope("prototype")
public class AnalyticsQueryBuilder {
    // values in Solr below cutoff are excluded after index build
    private static final String BASELINE_ABOVE_CUTOFF = "(experimentType:(rnaseq_mrna_baseline OR proteomics_baseline))";
    private static final String DIFFERENTIAL_ABOVE_CUTOFF =
            "(experimentType:(rnaseq_mrna_differential OR microarray_1colour_mrna_differential OR microarray_2colour_mrna_differential OR microarray_1colour_microrna_differential) " +
            "AND pValue:[* TO 0.05])";

    public enum Field {
        EXPERIMENT_TYPE("experimentType"),
        BIOENTITY_IDENTIFIER("bioentityIdentifier"),
        SPECIES("species");
        final String name;

        Field(String name){
            this.name = name;
        }

    }

    private static final String IDENTIFIER_SEARCH_FIELD = "identifierSearch";
    private static final String CONDITIONS_SEARCH_FIELD = "conditionsSearch";

    private SemanticQuery geneQuery = SemanticQuery.create();
    private SemanticQuery conditionQuery = SemanticQuery.create();
    private ArrayList<String> bioentityIdentifierTerms = new ArrayList<>();
    private ArrayList<String> speciesTerms = new ArrayList<>();
    private int facetLimit = -1;

    private SolrQuery solrQuery = new SolrQuery();


    public AnalyticsQueryBuilder facetBy(Field f){
        solrQuery.setFacet(true);
        solrQuery.setFacetMinCount(1);
        solrQuery.addFacetField(f.name);
        return this;
    }
    
    public AnalyticsQueryBuilder setFacetLimit(int facetLimit){
        this.facetLimit = facetLimit;
        return this;
    }


    public AnalyticsQueryBuilder setRows(int rows) {
        solrQuery.setRows(rows);
        return this;
    }

    public AnalyticsQueryBuilder queryIdentifierSearch(SemanticQuery geneQuery) {
        this.geneQuery = SemanticQuery.create(geneQuery.terms());
        return this;
    }

    public AnalyticsQueryBuilder queryConditionsSearch(SemanticQuery conditionQuery) {
        this.conditionQuery = SemanticQuery.create(conditionQuery.terms());
        return this;
    }


    public AnalyticsQueryBuilder ofSpecies(String species) {
        if (!Strings.isNullOrEmpty(species)) {
            speciesTerms.add(wrap(species, '"'));
        }
        return this;
    }

    public AnalyticsQueryBuilder filterAboveDefaultCutoff() {
        solrQuery.addFilterQuery(BASELINE_ABOVE_CUTOFF + " OR " + DIFFERENTIAL_ABOVE_CUTOFF);
        return this;
    }

    public SolrQuery build() {
        StringBuilder stringBuilder = new StringBuilder();
        Joiner joinerOr = Joiner.on(" OR ");

        if (geneQuery.isEmpty() && bioentityIdentifierTerms.size() == 0) {
            stringBuilder.append(Field.BIOENTITY_IDENTIFIER.name).append(":*");
        }

        if (geneQuery.isNotEmpty()) {
            stringBuilder.append(IDENTIFIER_SEARCH_FIELD).append(":(").append(geneQuery.asAnalyticsIndexQueryClause()).append(")");
        }

        if (bioentityIdentifierTerms.size() > 0) {
            if (stringBuilder.length() > 0) {
                stringBuilder.append(" OR ");
            }
            stringBuilder.append(Field.BIOENTITY_IDENTIFIER.name).append(":(").append(joinerOr.join(bioentityIdentifierTerms)).append(")");
        }

        if (conditionQuery.isNotEmpty()) {
            if (stringBuilder.length() > 0) {
                stringBuilder.append(" AND ");
            }
            stringBuilder.append(CONDITIONS_SEARCH_FIELD).append(":(").append(conditionQuery.asAnalyticsIndexQueryClause()).append(")");
        }

        if (speciesTerms.size() > 0) {
            if (stringBuilder.length() > 0) {
                stringBuilder.append(" AND ");
            }
            stringBuilder.append(Field.SPECIES.name).append(":(").append(joinerOr.join(speciesTerms)).append(")");
        }

        solrQuery.setQuery(stringBuilder.toString());
        solrQuery.setFacetLimit(facetLimit);
        return solrQuery;
    }
}
