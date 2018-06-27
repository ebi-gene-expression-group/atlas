package uk.ac.ebi.atlas.solr.cloud.collections;

import org.apache.solr.client.solrj.SolrClient;
import uk.ac.ebi.atlas.solr.cloud.CollectionProxy;
import uk.ac.ebi.atlas.solr.cloud.SchemaField;

public class Gene2ExperimentCollectionProxy extends CollectionProxy {
    public static class Gene2ExperimentSchemaField extends SchemaField<Gene2ExperimentCollectionProxy> {
        private Gene2ExperimentSchemaField(String fieldName) {
            super(fieldName);
        }
    }

    public static final Gene2ExperimentSchemaField BIOENTITY_IDENTIFIER = new Gene2ExperimentSchemaField("bioentity_identifier");
    public static final Gene2ExperimentSchemaField EXPERIMENT_ACCESSION = new Gene2ExperimentSchemaField("experiment_accession");

    public Gene2ExperimentCollectionProxy(SolrClient solrClient) {
        super(solrClient, "scxa-gene2experiment");
    }
}
