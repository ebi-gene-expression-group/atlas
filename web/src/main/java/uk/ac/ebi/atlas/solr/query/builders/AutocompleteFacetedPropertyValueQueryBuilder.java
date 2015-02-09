package uk.ac.ebi.atlas.solr.query.builders;

import org.apache.solr.client.solrj.SolrQuery;

/**
 * Created with IntelliJ IDEA.

 */
public class AutocompleteFacetedPropertyValueQueryBuilder extends SolrQueryBuilder<AutocompleteFacetedPropertyValueQueryBuilder> {

    private static final int DEFAULT_LIMIT = 15;
    public static final String PROPERTY_VALUE = "property_value";

    public SolrQuery buildPropertyValueAutocompleteQuery(String queryString) {

        String solrQueryString = "property_value_edgengram:\"" + queryString + "\"" + queryStringBuilder.toString();
        return buildQueryObject(solrQueryString);
    }

    //NB: facet results will be sorted by count
    private SolrQuery buildQueryObject(String queryString) {
        SolrQuery solrQuery = new SolrQuery(queryString);

        solrQuery.addFacetField(PROPERTY_VALUE);
        solrQuery.setRows(0);
        solrQuery.setFacet(true);
        solrQuery.setFacetLimit(DEFAULT_LIMIT);
        solrQuery.setFacetMinCount(1);

        return solrQuery;
    }


    @Override
    protected AutocompleteFacetedPropertyValueQueryBuilder getThis() {
        return this;
    }
}
