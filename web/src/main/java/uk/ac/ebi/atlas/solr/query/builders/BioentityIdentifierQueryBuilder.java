
package uk.ac.ebi.atlas.solr.query.builders;

import org.apache.solr.client.solrj.SolrQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.atlas.search.GeneQuery;

/**
 * This is a builder, keep always in mind that builders are potentially stateful.
 * If you need to build different query strings within the same client process
 * you must use a new instance of builder for each query.
 */
public class BioentityIdentifierQueryBuilder extends SolrQueryBuilder<BioentityIdentifierQueryBuilder>{
    private static final Logger LOGGER = LoggerFactory.getLogger(BioentityIdentifierQueryBuilder.class);

    private static final String BIOENTITY_IDENTIFIER_FIELD = "bioentity_identifier";
    private static final String PROPERTY_SEARCH_FIELD = "property_value_search";
    private static final int MAX_GENE_IDS_TO_FETCH = 1000000;

    private boolean applyOrOnQueryStringContent;
    private String queryString;

    public BioentityIdentifierQueryBuilder forQueryString(String queryString, boolean applyOrOnQueryStringContent){
        this.queryString = queryString.replace(":", "\\:").replace("[", "\\[").replace("]", "\\]");
        this.applyOrOnQueryStringContent = applyOrOnQueryStringContent;
        return this;
    }

    public BioentityIdentifierQueryBuilder forGeneQuery(GeneQuery geneQuery, boolean applyOrOnQueryStringContent){
        this.queryString = geneQuery.asSolr1DNF();
        this.applyOrOnQueryStringContent = applyOrOnQueryStringContent;
        return this;
    }

    public SolrQuery build() {
        String propertyName = PROPERTY_SEARCH_FIELD;
        StringBuilder queryConditionBuilder = new StringBuilder(propertyName).append(":");

        if (applyOrOnQueryStringContent){
            // use default "lucene" parser and set default operator to OR and default field to propertyName
            queryConditionBuilder.insert(0, "{!lucene q.op=OR df=" + propertyName + "}(").append(queryString).append(")");
        } else {
            queryConditionBuilder.append("\"").append(queryString).append("\"");
        }

        queryStringBuilder.insert(0, queryConditionBuilder);

        return buildQueryObject(queryStringBuilder.toString());
    }

    private SolrQuery buildQueryObject(String queryString) {
        SolrQuery solrQuery = new SolrQuery(queryString);
        solrQuery.setFields(BIOENTITY_IDENTIFIER_FIELD);
        solrQuery.setParam("group", true);
        solrQuery.setParam("group.field", BIOENTITY_IDENTIFIER_FIELD);
        solrQuery.setParam("group.main", true);
        solrQuery.setRows(MAX_GENE_IDS_TO_FETCH);

        LOGGER.trace("<buildQueryObject> solr query: {}", solrQuery.toString());

        return solrQuery;
    }

    @Override
    protected BioentityIdentifierQueryBuilder getThis() {
        return this;
    }
}
