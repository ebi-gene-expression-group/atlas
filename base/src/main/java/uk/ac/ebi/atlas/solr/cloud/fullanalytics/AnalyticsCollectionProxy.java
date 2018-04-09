package uk.ac.ebi.atlas.solr.cloud.fullanalytics;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrRequest;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.FieldStatsInfo;
import org.apache.solr.client.solrj.response.QueryResponse;
import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.solr.BioentityPropertyName;
import uk.ac.ebi.atlas.solr.cloud.CollectionProxy;
import uk.ac.ebi.atlas.solr.cloud.SchemaField;
import uk.ac.ebi.atlas.solr.cloud.search.SolrQueryBuilder;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.HashSet;
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
    public static final AnalyticsSchemaField EXPRESSION_LEVELS = new AnalyticsSchemaField("expression_levels");
    public static final AnalyticsSchemaField EXPRESSION_LEVELS_FPKM = new AnalyticsSchemaField("expression_levels_fpkm");
    public static final AnalyticsSchemaField LOG_2_FOLD_CHANGE = new AnalyticsSchemaField("fold_change");
    public static final AnalyticsSchemaField ADJUSTED_P_VALUE = new AnalyticsSchemaField("p_value");
    public static final AnalyticsSchemaField IDENTIFIER_SEARCH = new AnalyticsSchemaField("identifier_search");
    public static final AnalyticsSchemaField CONDITIONS_SEARCH = new AnalyticsSchemaField("conditions_search");
    public static final AnalyticsSchemaField SPECIES = new AnalyticsSchemaField("species");
    public static final AnalyticsSchemaField DEFAULT_FACTOR_TYPE = new AnalyticsSchemaField("default_query_factor_type");

    public static AnalyticsSchemaField asAnalyticsSchemaField(BioentityPropertyName bioentityPropertyName) {
        if (bioentityPropertyName == UNKNOWN) {
            return IDENTIFIER_SEARCH;
        }

        return bioentityPropertyName.isKeyword ?
                new AnalyticsSchemaField(String.format("keyword_%s", bioentityPropertyName.name)) :
                new AnalyticsSchemaField(bioentityPropertyName.name);
    }

    public static Map<AnalyticsSchemaField, Set<String>> asAnalyticsGeneQuery(SemanticQuery geneQuery) {
        Map<AnalyticsSchemaField, Set<String>> queryMap =
                geneQuery.groupValuesByCategory().entrySet().stream()
                .collect(toMap(
                        entry -> asAnalyticsSchemaField(BioentityPropertyName.getByName(entry.getKey())),
                        entry -> entry.getValue().stream()
                                                // Lower case is a hack se we can use the terms query parser on a
                                                // lowercase field. If only there existed a multiple field query
                                                // parser... :(
                                                 .map(String::toLowerCase)
                                                 .collect(toSet())));

        // Since there are gene IDs unavailable in bioentities because they’re in scaffolds which we don’t have or from
        // past Ensembl releases which we don’t support, the only way to search for such genes is through a free-text
        // search matched against the bioentity identifier. This requirement can be removed after

        Set<String> identifierSearchValues = queryMap.getOrDefault(IDENTIFIER_SEARCH, new HashSet<>());
        Set<String> bioentityIdentifierSearchValues = new HashSet<>(queryMap.getOrDefault(BIOENTITY_IDENTIFIER_SEARCH, new HashSet<>()));
        bioentityIdentifierSearchValues.addAll(identifierSearchValues);
        if (!bioentityIdentifierSearchValues.isEmpty()) {
            queryMap.put(BIOENTITY_IDENTIFIER_SEARCH, bioentityIdentifierSearchValues);
        }

        // We could implicitly change the contents of the map with just this... nasty!
        // queryMap.getOrDefault(BIOENTITY_IDENTIFIER_SEARCH, new HashSet<>()).addAll(identifierSearchValues);

        return queryMap;
    }

    public AnalyticsCollectionProxy(SolrClient solrClient) {
        super(solrClient, "analytics");
    }

    public QueryResponse queryWithBuilder(SolrQueryBuilder<AnalyticsCollectionProxy> solrQueryBuilder) {
        try {
            // Change maybe to: return new QueryRequest()
            return solrClient.query(nameOrAlias, solrQueryBuilder.build(), SolrRequest.METHOD.POST);
        } catch (IOException e) {
            logException(e);
            throw new UncheckedIOException(e);
        } catch (SolrServerException e) {
            logException(e);
            throw new UncheckedIOException(new IOException(e));
        }
    }

    public FieldStatsInfo fieldStats(SchemaField<AnalyticsCollectionProxy> field, SolrQuery solrQuery) {
        return super.fieldStats(field.name(), solrQuery);
    }
}
