package uk.ac.ebi.atlas.solr.cloud.bioentities;

import org.apache.solr.client.solrj.SolrClient;
import uk.ac.ebi.atlas.solr.cloud.CollectionProxy;
import uk.ac.ebi.atlas.solr.cloud.SchemaField;

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
        super(solrClient, "analytics");
    }
}
