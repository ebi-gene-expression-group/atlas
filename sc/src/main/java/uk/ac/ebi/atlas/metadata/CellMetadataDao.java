package uk.ac.ebi.atlas.metadata;

import com.google.common.collect.ImmutableMap;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.springframework.stereotype.Component;
import uk.ac.ebi.atlas.solr.cloud.SolrCloudCollectionProxyFactory;
import uk.ac.ebi.atlas.solr.cloud.fullanalytics.SingleCellAnalyticsCollectionProxy;
import uk.ac.ebi.atlas.solr.cloud.search.SolrQueryBuilder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static com.google.common.collect.ImmutableMap.toImmutableMap;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;

@Component
public class CellMetadataDao {
    private SingleCellAnalyticsCollectionProxy singleCellAnalyticsCollectionProxy;

    public CellMetadataDao(SolrCloudCollectionProxyFactory solrCloudCollectionProxyFactory) {
        this.singleCellAnalyticsCollectionProxy =
                solrCloudCollectionProxyFactory.createSingleCellAnalyticsCollectionProxy();
    }

    // This retrieves the value for one single-value field in the Solr scxa-analytics collection
    public Optional<String> getMetadataFieldValueForCellId(SingleCellAnalyticsCollectionProxy.SingleCellAnalyticsSchemaField metadataField, String experimentAccession, String cellId) {
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

    // Retrieves all the available factors stored in the Solr scxa-analytics collection for a particular cell
    public SingleCellAnalyticsCollectionProxy.SingleCellAnalyticsSchemaField[] getFactorFieldNames(String experimentAccession, String cellId) {
        Map<String, Collection<Object>> queryResult = getCellQueryResultForMultiValueFields(
                experimentAccession,
                cellId,
                SingleCellAnalyticsCollectionProxy.FACTORS);

        return queryResult.getOrDefault(SingleCellAnalyticsCollectionProxy.FACTORS.name(), Collections.emptyList())
                .stream()
                .filter(factor -> !factor.toString().equalsIgnoreCase("single_cell_identifier"))
                .map(factor -> SingleCellAnalyticsCollectionProxy.factorAsSchemaField(factor.toString()))
                .toArray(SingleCellAnalyticsCollectionProxy.SingleCellAnalyticsSchemaField[]::new);
    }

    // Returns Solr query results for a list of multi-value fields of interest
    public ImmutableMap<String, Collection<Object>> getCellQueryResultForMultiValueFields(
            String experimentAccession, String cellId, SingleCellAnalyticsCollectionProxy.SingleCellAnalyticsSchemaField... fieldsOfInterest) {
        if (fieldsOfInterest.length == 0) {
            return ImmutableMap.of();
        }

        SolrQueryBuilder<SingleCellAnalyticsCollectionProxy> solrQueryBuilder =
                new SolrQueryBuilder<SingleCellAnalyticsCollectionProxy>()
                        .addFilterFieldByTerm(SingleCellAnalyticsCollectionProxy.EXPERIMENT_ACCESSION, experimentAccession)
                        .addQueryFieldByTerm(SingleCellAnalyticsCollectionProxy.CELL_ID, cellId)
                        .setFieldList(fieldsOfInterest)
                        .setRows(1);

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

    // A metadata category is a Solr field where metadata is stored. Most of the time this will be a factor_* field,
    // but fields such as characteristic_inferred_cell_type are also considered metadata.
    public Map<String, String> getValuesForMetadataCategory(String experimentAccession, SingleCellAnalyticsCollectionProxy.SingleCellAnalyticsSchemaField metadataCategory, List<String> cellIds) {
        SolrQueryBuilder<SingleCellAnalyticsCollectionProxy> solrQueryBuilder =
                new SolrQueryBuilder<SingleCellAnalyticsCollectionProxy>()
                        .addQueryFieldByTerm(SingleCellAnalyticsCollectionProxy.EXPERIMENT_ACCESSION, experimentAccession)
                        .addQueryFieldByTerm(SingleCellAnalyticsCollectionProxy.CELL_ID, cellIds)
                        .setFieldList(metadataCategory, SingleCellAnalyticsCollectionProxy.CELL_ID);

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
                                entry -> (String) ((ArrayList) entry.getValue().get(0).getFieldValue(metadataCategory.name())).get(0))
                );
    }
}
