package uk.ac.ebi.atlas.search.analyticsindex.solr;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.search.SemanticQuery;

import javax.inject.Named;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

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
        SPECIES("species"),
        IDENTIFIER_SEARCH("identifierSearch"),
        CONDITIONS_SEARCH("conditionsSearch");
        final String name;

        Field(String name){
            this.name = name;
        }

    }

    private SemanticQuery geneQuery = SemanticQuery.create();
    private SemanticQuery conditionQuery = SemanticQuery.create();
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

    private String queryTerm(Field field, String clause){
        return String.format("%s:(%s)", field.name, clause);
    }

    public SolrQuery build() {
        if (geneQuery.isNotEmpty() || conditionQuery.isNotEmpty() || speciesTerms.size() > 0) {
            Collection<String> queryTerms = new HashSet<>();
            if (geneQuery.isNotEmpty()) {
                queryTerms.add(queryTerm(Field.IDENTIFIER_SEARCH, geneQuery.asAnalyticsIndexQueryClause()));
            }
            if (conditionQuery.isNotEmpty()) {
                queryTerms.add(queryTerm(Field.CONDITIONS_SEARCH, conditionQuery.asAnalyticsIndexQueryClause()));
            }
            if (speciesTerms.size() > 0) {
                queryTerms.add(queryTerm(Field.SPECIES,  Joiner.on(" OR ").join(speciesTerms)));
            }
            solrQuery.setQuery(Joiner.on(" AND ").join(queryTerms));
        } else {
            solrQuery.setQuery(queryTerm(Field.BIOENTITY_IDENTIFIER, "*"));
        }
        solrQuery.setFacetLimit(facetLimit);
        return solrQuery;
    }
}
