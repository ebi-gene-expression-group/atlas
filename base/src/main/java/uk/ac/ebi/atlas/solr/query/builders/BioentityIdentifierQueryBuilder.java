package uk.ac.ebi.atlas.solr.query.builders;

import org.apache.solr.client.solrj.SolrQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.atlas.search.SemanticQueryTerm;
import uk.ac.ebi.atlas.species.Species;

/**
 * This is a builder, keep always in mind that builders are potentially stateful.
 * If you need to build different query strings within the same client process
 * you must use a new instance of builder for each query.
 */
public class BioentityIdentifierQueryBuilder {
    private static final Logger LOGGER = LoggerFactory.getLogger(BioentityIdentifierQueryBuilder.class);

    private static final String BIOENTITY_IDENTIFIER_FIELD = "bioentity_identifier";
    private static final String SPECIES_FIELD = "species";
    private static final int MAX_GENE_IDS_TO_FETCH = 1000000;

    private StringBuilder queryStringBuilder = new StringBuilder();
    private String queryString;

    public BioentityIdentifierQueryBuilder forTerm(SemanticQueryTerm term){
        this.queryString = term.asBioentitiesIndexQueryLiteral();
        return this;
    }

    public BioentityIdentifierQueryBuilder withSpecies(Species species){
        // No need to lowercase the species field, it is of type lowercase in Solr
        if (!species.isUnknown()){
            queryStringBuilder.append(" AND " + SPECIES_FIELD + ":\"").append(species.getEnsemblName()).append("\"");
        }
        return this;
    }

    public SolrQuery build() {
        // use default "lucene" parser and set default operator to OR and default field to propertyName
        StringBuilder queryConditionBuilder = new StringBuilder("{!lucene q.op=OR df=property_value_search}");
        queryConditionBuilder.append(String.format("(%s)", queryString));

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

}
