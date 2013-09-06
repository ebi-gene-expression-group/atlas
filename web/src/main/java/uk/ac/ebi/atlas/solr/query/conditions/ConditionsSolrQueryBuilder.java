package uk.ac.ebi.atlas.solr.query.conditions;

import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.context.annotation.Scope;

import javax.inject.Named;

@Named
@Scope("prototype")
public class ConditionsSolrQueryBuilder {

    public static final String PROPERTY_VALUE_SEARCH_FIELD = "property_value_search";

    public SolrQuery buildFullTestSearchQuery(String queryString) {

        SolrQuery solrQuery = new SolrQuery(buildQueryString(queryString));
        return solrQuery;
    }

    String buildQueryString(String queryString){
        return PROPERTY_VALUE_SEARCH_FIELD + ":" + queryString.trim();
    }
}
