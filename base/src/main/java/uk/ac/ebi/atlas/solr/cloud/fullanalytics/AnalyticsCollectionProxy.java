package uk.ac.ebi.atlas.solr.cloud.fullanalytics;

import org.apache.solr.client.solrj.SolrClient;
import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.solr.BioentityPropertyName;
import uk.ac.ebi.atlas.solr.cloud.CollectionProxy;
import uk.ac.ebi.atlas.solr.cloud.SchemaField;

import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;
import static uk.ac.ebi.atlas.solr.BioentityPropertyName.UNKNOWN;

public class AnalyticsCollectionProxy extends CollectionProxy {
    public static class AnalyticsSchemaField extends SchemaField<AnalyticsCollectionProxy> {
        private AnalyticsSchemaField(String fieldName) {
            super(fieldName);
        }
    }

    public static final AnalyticsSchemaField BIOENTITY_IDENTIFIER = new AnalyticsSchemaField("bioentity_identifier");
    public static final AnalyticsSchemaField BIOENTITY_IDENTIFIER_SEARCH = new AnalyticsSchemaField("bioentity_identifier_search");
    public static final AnalyticsSchemaField EXPERIMENT_ACCESSION = new AnalyticsSchemaField("experiment_accession");
    public static final AnalyticsSchemaField ASSAY_GROUP_ID = new AnalyticsSchemaField("assay_group_id");
    public static final AnalyticsSchemaField CONTRAST_ID = new AnalyticsSchemaField("contrast_id");
    public static final AnalyticsSchemaField EXPRESSION_LEVEL = new AnalyticsSchemaField("expression_level");
    public static final AnalyticsSchemaField EXPRESSION_LEVEL_FPKM = new AnalyticsSchemaField("expression_level_fpkm");
    public static final AnalyticsSchemaField LOG_2_FOLD_CHANGE = new AnalyticsSchemaField("fold_change");
    public static final AnalyticsSchemaField ADJUSTED_P_VALUE = new AnalyticsSchemaField("p_value");
    public static final AnalyticsSchemaField IDENTIFIER_SEARCH = new AnalyticsSchemaField("identifier_search");

    public static AnalyticsSchemaField asAnalyticsSchemaField(BioentityPropertyName bioentityPropertyName) {
        if (bioentityPropertyName == UNKNOWN) {
            return IDENTIFIER_SEARCH;
        }

        return bioentityPropertyName.isKeyword ?
                new AnalyticsSchemaField(String.format("keyword_%s", bioentityPropertyName.name)) :
                new AnalyticsSchemaField(bioentityPropertyName.name);
    }

    public static Map<AnalyticsSchemaField, Set<String>> asAnalyticsGeneQuery(SemanticQuery geneQuery) {
        return geneQuery.groupValuesByCategory().entrySet().stream()
                .collect(toMap(
                        entry -> asAnalyticsSchemaField(BioentityPropertyName.getByName(entry.getKey())),
                        entry -> entry.getValue().stream()
                                                // Lower case is a hack to use the terms query parser on a lowercase
                                                // field. This has the drawback that Solr details leak all the way
                                                // to here. If only there existed a multiple field query parser... :(
                                                 .map(String::toLowerCase)
                                                 .collect(toSet())));
    }

    public AnalyticsCollectionProxy(SolrClient solrClient) {
        super(solrClient, "analytics");
    }
}
