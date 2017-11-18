package uk.ac.ebi.atlas.solr.analytics.query;

import com.google.common.collect.Sets;
import org.apache.solr.client.solrj.SolrQuery;

import java.util.HashSet;

public class AnalyticsQueryBuilder {
    // Observed a slowdown in terms queries that exceed >50,000 terms
    private static final int TERMS_QUERY_SIZE_THRESHOLD = 25000;

    // Solr defaults
    private int rows = 10;
    private HashSet<String> filterQueries = Sets.newHashSet();
    private String query = "";
    private String jsonFacet = "";

    public AnalyticsQueryBuilder setRows(int rows) {
        this.rows = rows;
        return this;
    }

    public AnalyticsQueryBuilder addFilterQuery(String filterQuery) {
        filterQueries.add(filterQuery);
        return this;
    }

    public AnalyticsQueryBuilder clearFilterQueries() {
        filterQueries.clear();
        return this;
    }

    public AnalyticsQueryBuilder queryAll() {
        query = "*:*";
        return this;
    }

    public SolrQuery build() {
        SolrQuery solrQuery = new SolrQuery();

        solrQuery.set("json.facet", jsonFacet);
        return solrQuery
                .setRows(0)
                .setFilterQueries(filterQueries.toArray(new String[0]))
                .setQuery(query);
    }

}
