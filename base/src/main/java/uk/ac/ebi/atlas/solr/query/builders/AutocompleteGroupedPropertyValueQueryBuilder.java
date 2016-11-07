package uk.ac.ebi.atlas.solr.query.builders;

import org.apache.solr.client.solrj.SolrQuery;

/**
 * Created with IntelliJ IDEA.

 */
public class AutocompleteGroupedPropertyValueQueryBuilder extends SolrQueryBuilder<AutocompleteGroupedPropertyValueQueryBuilder> {

    private static final int DEFAULT_LIMIT = 15;
    public static final String PROPERTY_VALUE = "property_value";

    public SolrQuery build(String queryString) {

        String solrQueryString = "property_value_edgengram:\"" + queryString + "\"" + queryStringBuilder.toString();
        return buildQueryObject(solrQueryString);
    }

    //NB: results will be sorted by score
    private SolrQuery buildQueryObject(String queryString) {
        SolrQuery solrQuery = new SolrQuery(queryString);

        solrQuery.setFields(PROPERTY_VALUE, SolrQueryBuilder.PROPERTY_NAME_FIELD);
        solrQuery.setParam("group", true);
        solrQuery.setParam("group.field", PROPERTY_VALUE);
        solrQuery.setParam("group.main", true);
        solrQuery.setRows(DEFAULT_LIMIT);

        return solrQuery;
    }


    @Override
    protected AutocompleteGroupedPropertyValueQueryBuilder getThis() {
        return this;
    }
}
