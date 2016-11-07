package uk.ac.ebi.atlas.solr.query.builders;

import org.apache.solr.client.solrj.SolrQuery;

/**
 * This is a builder, keep always in mind that builders are potentially stateful.
 * If you need to build different query strings within the same client process
 * you must use a new instance of builder for each query.
 */
public class FacetedPropertyValueQueryBuilder extends SolrQueryBuilder<FacetedPropertyValueQueryBuilder>{

    private static final int DEFAULT_LIMIT = 15;
    public static final String PROPERTY_LOWER_FIELD = "property_value_lower";

    public SolrQuery buildPropertyValueAutocompleteQuery(String queryString) {
        String solrQueryString = "property_value_edgengram:\"" + queryString + "\"" + queryStringBuilder.toString();
        return buildQueryObject(solrQueryString);
    }

    public SolrQuery buildBioentityQuery(String bioentityId) {
        String solrQueryString = "bioentity_identifier:\"" + bioentityId + "\"" + queryStringBuilder.toString();
        return buildQueryObject(solrQueryString);
    }

    private SolrQuery buildQueryObject(String queryString) {
        SolrQuery solrQuery = new SolrQuery(queryString);

        solrQuery.addFacetField(PROPERTY_LOWER_FIELD);
        solrQuery.setRows(0);
        solrQuery.setFacet(true);
        solrQuery.setFacetLimit(DEFAULT_LIMIT);
        solrQuery.setFacetMinCount(1);

        return solrQuery;
    }

    @Override
    protected FacetedPropertyValueQueryBuilder getThis() {
        return this;
    }
}
