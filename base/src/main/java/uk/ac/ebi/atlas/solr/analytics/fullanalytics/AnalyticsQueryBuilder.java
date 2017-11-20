package uk.ac.ebi.atlas.solr.analytics.fullanalytics;

import com.google.common.collect.Sets;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.util.ClientUtils;

import java.text.MessageFormat;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class AnalyticsQueryBuilder {
    enum Field {
        EXPERIMENT_ACCESSION("experiment_accession"),
        BIOENTITY_IDENTIFIER("bioentity_identifier"),
        ASSAY_GROUP_ID("assay_group_id"),
        EXPRESSION_LEVEL("expression_level");
        final String name;

        Field(String name){
            this.name = name;
        }

        @Override
        public String toString() {
            return this.name;
        }
    }

    private static final String SIMPLE_QUERY_TEMPLATE = "{0}:\"{1}\"";
    private static final String TERMS_QUERY_TEMPLATE = "('{'!terms f={0}'}'{1})";
    private static final String RANGE_QUERY_TEMPLATE = "{0}:[{1} TO *]";

    // Solr defaults
    private HashSet<String> filterQueries = Sets.newHashSet();
    private HashSet<String> queries = Sets.newHashSet();
    private String jsonFacet = "";

    public AnalyticsQueryBuilder() {
        filterQueries.add("expression_level:{0.0 TO *]");
    }

    public AnalyticsQueryBuilder filterExperimentAccession(String experimentAccession) {
        filterQueries.add(createSimpleQuery(Field.EXPERIMENT_ACCESSION, experimentAccession));
        return this;
    }

    public AnalyticsQueryBuilder filterAssayGroupIds(Set<String> assayGroupIds) {
        filterQueries.add(createTermsQuery(Field.ASSAY_GROUP_ID, assayGroupIds));
        return this;
    }


    private String createSimpleQuery(Field searchField, String searchValue) {
        return MessageFormat.format(SIMPLE_QUERY_TEMPLATE, searchField.name, ClientUtils.escapeQueryChars(searchValue));
    }

    public AnalyticsQueryBuilder queryAssayGroupIds(Set<String> assayGroupIds) {
        queries.add(createTermsQuery(Field.ASSAY_GROUP_ID, assayGroupIds));
        return this;
    }

    public AnalyticsQueryBuilder queryNotAssayGroupIds(Set<String> assayGroupIds) {
        queries.add("-" + createTermsQuery(Field.ASSAY_GROUP_ID, assayGroupIds));
        return this;
    }


    public AnalyticsQueryBuilder queryBioentityIdentifiers(Set<String> bioentityIdentifiers) {
        queries.add(createTermsQuery(Field.BIOENTITY_IDENTIFIER, bioentityIdentifiers));
        return this;
    }

    private String createTermsQuery(Field searchField, Set<String> searchValues) {
        return MessageFormat.format(
                TERMS_QUERY_TEMPLATE,
                searchField.name,
                searchValues.stream().map(ClientUtils::escapeQueryChars).collect(Collectors.joining(",")));
    }

    public AnalyticsQueryBuilder queryExpressionLevel(Double expressionLevel) {
        queries.add(createRangeQuery(Field.EXPRESSION_LEVEL, expressionLevel));
        return this;
    }

    private String createRangeQuery(Field searchField, Double rangeStart) {
        return MessageFormat.format(RANGE_QUERY_TEMPLATE, searchField.name, rangeStart.toString());
    }

    public AnalyticsQueryBuilder setJsonFacet(String jsonFacet) {
        this.jsonFacet = jsonFacet;
        return this;
    }

    public SolrQuery build() {
        SolrQuery solrQuery = new SolrQuery();

        solrQuery.set("json.facet", jsonFacet);
        return solrQuery
                .setRows(0)
                .setFilterQueries(filterQueries.toArray(new String[0]))
                .setQuery(queries.stream().collect(Collectors.joining(" AND ")));
    }
}
