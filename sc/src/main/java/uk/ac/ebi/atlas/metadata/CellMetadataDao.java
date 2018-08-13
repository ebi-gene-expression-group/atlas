package uk.ac.ebi.atlas.metadata;

import com.google.common.collect.ImmutableMap;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.springframework.stereotype.Component;
import uk.ac.ebi.atlas.solr.cloud.SolrCloudCollectionProxyFactory;
import uk.ac.ebi.atlas.solr.cloud.collections.SingleCellAnalyticsCollectionProxy;
import uk.ac.ebi.atlas.solr.cloud.search.SolrQueryBuilder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.google.common.collect.ImmutableMap.toImmutableMap;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;

@Component
public class CellMetadataDao {
    private SingleCellAnalyticsCollectionProxy singleCellAnalyticsCollectionProxy;

    public CellMetadataDao(SolrCloudCollectionProxyFactory solrCloudCollectionProxyFactory) {
        this.singleCellAnalyticsCollectionProxy =
                solrCloudCollectionProxyFactory.create(SingleCellAnalyticsCollectionProxy.class);
    }

    // Retrieves a list of metadata fields available in Solr for a particular experiment. This includes all factor fields
    // (except single_cell_identifier), as well as the characteristic_inferred_cell_type field.
    public List<SingleCellAnalyticsCollectionProxy.SingleCellAnalyticsSchemaField> getMetadataFieldNames(String experimentAccession) {
        Map<String, Collection<Object>> queryResult = getQueryResultForMultiValueFields(
                experimentAccession,
                Optional.empty(),
                SingleCellAnalyticsCollectionProxy.FACTORS, SingleCellAnalyticsCollectionProxy.CHARACTERISTIC_INFERRED_CELL_TYPE);

        List<SingleCellAnalyticsCollectionProxy.SingleCellAnalyticsSchemaField> metadataFields = queryResult.getOrDefault(SingleCellAnalyticsCollectionProxy.FACTORS.name(), Collections.emptyList())
                .stream()
                .filter(factor -> !factor.toString().equalsIgnoreCase("single_cell_identifier"))
                .map(factor -> SingleCellAnalyticsCollectionProxy.factorAsSchemaField(factor.toString()))
                .collect(Collectors.toList());

        if(queryResult.containsKey(SingleCellAnalyticsCollectionProxy.CHARACTERISTIC_INFERRED_CELL_TYPE.name())) {
            metadataFields.add(SingleCellAnalyticsCollectionProxy.CHARACTERISTIC_INFERRED_CELL_TYPE);
        }

        return metadataFields;
    }

    // Retrieves all the available factors stored in the Solr scxa-analytics collection for a particular cell
    public SingleCellAnalyticsCollectionProxy.SingleCellAnalyticsSchemaField[] getFactorFieldNames(String experimentAccession, String cellId) {
        Map<String, Collection<Object>> queryResult = getQueryResultForMultiValueFields(
                experimentAccession,
                Optional.of(cellId),
                SingleCellAnalyticsCollectionProxy.FACTORS);

        return queryResult.getOrDefault(SingleCellAnalyticsCollectionProxy.FACTORS.name(), Collections.emptyList())
                .stream()
                .filter(factor -> !factor.toString().equalsIgnoreCase("single_cell_identifier"))
                .map(factor -> SingleCellAnalyticsCollectionProxy.factorAsSchemaField(factor.toString()))
                .toArray(SingleCellAnalyticsCollectionProxy.SingleCellAnalyticsSchemaField[]::new);
    }

    // Returns Solr query results for a list of multi-value fields of interest
    public ImmutableMap<String, Collection<Object>> getQueryResultForMultiValueFields(
            String experimentAccession, Optional<String> cellId, SingleCellAnalyticsCollectionProxy.SingleCellAnalyticsSchemaField... fieldsOfInterest) {
        if (fieldsOfInterest.length == 0) {
            return ImmutableMap.of();
        }

        SolrQueryBuilder<SingleCellAnalyticsCollectionProxy> solrQueryBuilder =
                new SolrQueryBuilder<SingleCellAnalyticsCollectionProxy>()
                        .addFilterFieldByTerm(SingleCellAnalyticsCollectionProxy.EXPERIMENT_ACCESSION, experimentAccession)
                        .setFieldList(fieldsOfInterest)
                        .setRows(1);

        cellId.ifPresent(s -> solrQueryBuilder.addQueryFieldByTerm(SingleCellAnalyticsCollectionProxy.CELL_ID, s));

        QueryResponse queryResponse = this.singleCellAnalyticsCollectionProxy.query(solrQueryBuilder);

        if (queryResponse.getResults().isEmpty()) {
            return ImmutableMap.of();
        }

        // The map created by getFieldValuesMap has, for a reason I can’t grasp, quite a few unsupported operations.
        // Among them entrySet, which is the basis for all streaming methods in maps and forEach D:
        SolrDocument solrDocument = queryResponse.getResults().get(0);
        return solrDocument.getFieldNames().stream()
                .collect(toImmutableMap(Function.identity(), solrDocument::getFieldValues));
    }

    // Given a Solr field where metadata is stored, this method retrieves the value of that field for a cell ID.
    public Optional<String> getMetadataValueForCellId(String experimentAccession, SingleCellAnalyticsCollectionProxy.SingleCellAnalyticsSchemaField metadataField, String cellId) {
        SolrQueryBuilder<SingleCellAnalyticsCollectionProxy> solrQueryBuilder =
                new SolrQueryBuilder<SingleCellAnalyticsCollectionProxy>()
                        .addFilterFieldByTerm(SingleCellAnalyticsCollectionProxy.EXPERIMENT_ACCESSION, experimentAccession)
                        .addQueryFieldByTerm(SingleCellAnalyticsCollectionProxy.CELL_ID, cellId)
                        .setFieldList(metadataField);
        QueryResponse queryResponse = this.singleCellAnalyticsCollectionProxy.query(solrQueryBuilder);

        if (queryResponse.getResults().isEmpty() ||
                !queryResponse.getResults().get(0).containsKey(metadataField.name())) {
            return Optional.empty();
        }

        return Optional.of((String) queryResponse.getResults().get(0).getFirstValue(metadataField.name()));
    }

    // Given a Solr field where metadata is stored, this method retrieves the value of that field for list of cell IDs.
    public Map<String, String> getMetadataValueForCellIds(String experimentAccession, SingleCellAnalyticsCollectionProxy.SingleCellAnalyticsSchemaField metadataField, List<String> cellIds) {
        SolrQueryBuilder<SingleCellAnalyticsCollectionProxy> solrQueryBuilder =
                new SolrQueryBuilder<SingleCellAnalyticsCollectionProxy>()
                        .addQueryFieldByTerm(SingleCellAnalyticsCollectionProxy.EXPERIMENT_ACCESSION, experimentAccession)
                        .addQueryFieldByTerm(SingleCellAnalyticsCollectionProxy.CELL_ID, cellIds)
                        .setFieldList(metadataField, SingleCellAnalyticsCollectionProxy.CELL_ID);

        QueryResponse response = singleCellAnalyticsCollectionProxy.query(solrQueryBuilder);

        return response.getResults()
                .stream()
                .collect(groupingBy(solrDocument -> (String) solrDocument.getFieldValue("cell_id")))
                .entrySet()
                .stream()
                .collect(
                        toMap(
                                Map.Entry::getKey,
                                // The factor fields in Solr are all multi-value fields, even though they technically shouldn't be.
                                // Apparently we don't expect any cell ID to have more than one factor value. This was confirmed
                                // by curators in this Slack conversation: https://ebi-fg.slack.com/archives/C800ZEPPS/p1529592962001046
                                entry -> (String) ((ArrayList) entry.getValue().get(0).getFieldValue(metadataField.name())).get(0))
                );
    }
}
