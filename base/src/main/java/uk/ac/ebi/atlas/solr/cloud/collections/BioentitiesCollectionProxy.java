package uk.ac.ebi.atlas.solr.cloud.collections;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import uk.ac.ebi.atlas.search.SemanticQueryTerm;
import uk.ac.ebi.atlas.solr.cloud.CollectionProxy;
import uk.ac.ebi.atlas.solr.cloud.SchemaField;
import uk.ac.ebi.atlas.solr.cloud.search.SolrQueryBuilder;

import static uk.ac.ebi.atlas.utils.StringUtil.escapeDoubleQuotes;

public class BioentitiesCollectionProxy extends CollectionProxy {
    public static class BioentitiesSchemaField extends SchemaField<BioentitiesCollectionProxy> {
        private BioentitiesSchemaField(String fieldName) {
            super(fieldName);
        }
    }

    public static final BioentitiesSchemaField BIOENTITY_IDENTIFIER = new BioentitiesSchemaField("bioentity_identifier");
    public static final BioentitiesSchemaField SPECIES = new BioentitiesSchemaField("species");
    public static final BioentitiesSchemaField PROPERTY_NAME = new BioentitiesSchemaField("property_name");
    public static final BioentitiesSchemaField PROPERTY_VALUE = new BioentitiesSchemaField("property_value");

    public BioentitiesCollectionProxy(SolrClient solrClient) {
        super(solrClient, "bioentities");
    }

    public static String asBioentitiesCollectionQuery(SemanticQueryTerm geneQuery) {
        return geneQuery.category()
                .map(
                        category ->
                                String.format(
                                        PROPERTY_NAME.name() + ":\"%s\" AND " + PROPERTY_VALUE.name() + ":\"%s\"",
                                        escapeDoubleQuotes(category),
                                        escapeDoubleQuotes(geneQuery.value())))
                .orElse(String.format(PROPERTY_VALUE.name() + ":\"%s\"", escapeDoubleQuotes(geneQuery.value())));
    }

    public QueryResponse query(SolrQueryBuilder<BioentitiesCollectionProxy> solrQueryBuilder) {
        return query(solrQueryBuilder.build());
    }
}
