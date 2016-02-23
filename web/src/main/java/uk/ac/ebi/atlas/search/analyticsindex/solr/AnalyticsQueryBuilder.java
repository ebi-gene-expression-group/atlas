package uk.ac.ebi.atlas.search.analyticsindex.solr;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.web.GeneQuery;
import uk.ac.ebi.atlas.web.SemanticQuery;
import uk.ac.ebi.atlas.web.SemanticQueryTerm;

import javax.inject.Named;

import java.util.ArrayList;

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

    private static final String IDENTIFIER_SEARCH_VALUE_TEMPLATE = "%s:{%s}";

    private static final String EXPERIMENT_TYPE_FIELD = "experimentType";
    private static final String BIOENTITY_IDENTIFIER_FIELD = "bioentityIdentifier";
    private static final String IDENTIFIER_SEARCH_FIELD = "identifierSearch";
    private static final String SPECIES_FIELD = "species";

    private ArrayList<String> identifierSearchTerms = new ArrayList<>();
    private ArrayList<String> bioentityIdentifierTerms = new ArrayList<>();
    private ArrayList<String> speciesTerms = new ArrayList<>();

    private SolrQuery solrQuery = new SolrQuery();

    public AnalyticsQueryBuilder facetByExperimentType() {
        solrQuery.setFacet(true);
        solrQuery.setFacetMinCount(1);
        solrQuery.addFacetField(EXPERIMENT_TYPE_FIELD);
        return this;
    }


    public AnalyticsQueryBuilder facetByBioentityIdentifier() {
        solrQuery.setFacet(true);
        solrQuery.setFacetMinCount(1);
        solrQuery.addFacetField(BIOENTITY_IDENTIFIER_FIELD);
        return this;
    }


    public AnalyticsQueryBuilder setRows(int rows) {
        solrQuery.setRows(rows);
        return this;
    }


    public AnalyticsQueryBuilder queryIdentifierSearch(GeneQuery geneQuery) {
        ImmutableList.Builder<String> builder = ImmutableList.builder();
        for (String term : geneQuery) {
            if (!StringUtils.isBlank(term)) {
                builder.add(quote(term));
            }
        }
        identifierSearchTerms.addAll(builder.build());

        return this;
    }


    public AnalyticsQueryBuilder queryIdentifierSearch(SemanticQuery semanticQuery) {
        ImmutableList.Builder<String> builder = ImmutableList.builder();
        for (SemanticQueryTerm term : semanticQuery) {
            if (term.hasValue()) {
                if (term.hasNoSource()) {
                    builder.add(quote(term.value()));
                } else {
                    builder.add(quote(String.format(IDENTIFIER_SEARCH_VALUE_TEMPLATE, term.source(), term.value())));
                }
            }
        }
        identifierSearchTerms.addAll(builder.build());

        return this;
    }


    public AnalyticsQueryBuilder queryBioentityIdentifier(String identifier) {
        if (!Strings.isNullOrEmpty(identifier)) {
            bioentityIdentifierTerms.add(quote(identifier));
        }
        return this;
    }


    public AnalyticsQueryBuilder ofSpecies(String species) {
        if (!Strings.isNullOrEmpty(species)) {
            speciesTerms.add(quote(species));
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

        if (identifierSearchTerms.size() > 0) {
            stringBuilder.append(IDENTIFIER_SEARCH_FIELD).append(":(").append(joinerOr.join(identifierSearchTerms)).append(")");
        }

        if (bioentityIdentifierTerms.size() > 0) {
            if (stringBuilder.length() > 0) {
                stringBuilder.append(" OR ");
            }
            stringBuilder.append(BIOENTITY_IDENTIFIER_FIELD).append(":(").append(joinerOr.join(bioentityIdentifierTerms)).append(")");
        }

        if (speciesTerms.size() > 0) {
            if (stringBuilder.length() > 0) {
                stringBuilder.append(" AND ");
            }
            stringBuilder.append(SPECIES_FIELD).append(":(").append(joinerOr.join(speciesTerms)).append(")");
        }

        solrQuery.setQuery(stringBuilder.toString());
        return solrQuery;
    }

}
