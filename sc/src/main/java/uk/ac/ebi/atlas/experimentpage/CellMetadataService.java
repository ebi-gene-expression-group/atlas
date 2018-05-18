package uk.ac.ebi.atlas.experimentpage;

import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.springframework.stereotype.Component;
import uk.ac.ebi.atlas.experimentimport.idf.IdfParser;
import uk.ac.ebi.atlas.experimentimport.idf.IdfParserOutput;
import uk.ac.ebi.atlas.solr.cloud.SolrCloudCollectionProxyFactory;
import uk.ac.ebi.atlas.solr.cloud.fullanalytics.SingleCellAnalyticsCollectionProxy;
import uk.ac.ebi.atlas.solr.cloud.search.SolrQueryBuilder;
import uk.ac.ebi.atlas.solr.utils.SchemaFieldNameUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.google.common.collect.ImmutableMap.toImmutableMap;
import static uk.ac.ebi.atlas.solr.cloud.fullanalytics.SingleCellAnalyticsCollectionProxy.SingleCellAnalyticsSchemaField;

@Component
public class CellMetadataService {

    private IdfParser idfParser;
    private SingleCellAnalyticsCollectionProxy singleCellAnalyticsCollectionProxy;

    public CellMetadataService(IdfParser idfParser, SolrCloudCollectionProxyFactory solrCloudCollectionProxyFactory) {
        this.idfParser = idfParser;
        this.singleCellAnalyticsCollectionProxy =
                solrCloudCollectionProxyFactory.createSingleCellAnalyticsCollectionProxy();
    }

    public Optional<String> getInferredCellType(String experimentAccession, String cellId) {
        return getMetadataFieldValueForCellId(
                SingleCellAnalyticsCollectionProxy.CHARACTERISTIC_INFERRED_CELL_TYPE,
                experimentAccession,
                cellId);
    }

    public Map<String, String> getFactors(String experimentAccession, String cellId) {
        return getCellQueryResultForMultiValueFields(experimentAccession, cellId, getFactorFieldNames(experimentAccession, cellId))
                .entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().stream().map(Object::toString).collect(Collectors.joining(","))));
    }

    // This is not currently used but leaving in for now in case the logic is still useful
    public Map<String, String> getIdfFileAttributes(String experimentAccession, String cellId) {
        Map<String, String> attributes = new HashMap<>();

        IdfParserOutput idfParserOutput = idfParser.parse(experimentAccession);

        if(!idfParserOutput.getMetadataFieldsOfInterest().isEmpty()) {

            SingleCellAnalyticsSchemaField[] attributeFields = idfParserOutput.getMetadataFieldsOfInterest()
                    .stream()
                    .map(attribute -> SingleCellAnalyticsCollectionProxy.characteristicAsSchemaField(SchemaFieldNameUtils.attributeNameToFieldName((attribute))))
                    .toArray(SingleCellAnalyticsSchemaField[]::new);

            Map<String, Collection<Object>> results = getCellQueryResultForMultiValueFields(experimentAccession, cellId, attributeFields);

            for(String attributeId : results.keySet()) {
                String factorValue = StringUtils.join(results.get(attributeId), ",");

                attributes.put(attributeId, factorValue);
            }
        }

        return attributes;
    }

    // This retrieves the value for one single-value field in the Solr scxa-analytics collection
    private Optional<String> getMetadataFieldValueForCellId(SingleCellAnalyticsSchemaField metadataField, String experimentAccession, String cellId) {
        SolrQueryBuilder<SingleCellAnalyticsCollectionProxy> solrQueryBuilder =
                new SolrQueryBuilder<SingleCellAnalyticsCollectionProxy>()
                        .addFilterFieldByTerm(SingleCellAnalyticsCollectionProxy.EXPERIMENT_ACCESSION, experimentAccession)
                        .addQueryFieldByTerm(SingleCellAnalyticsCollectionProxy.CELL_ID, cellId)
                        .setFieldList(metadataField);
        QueryResponse queryResponse = this.singleCellAnalyticsCollectionProxy.query(solrQueryBuilder);

        if (!queryResponse.getResults().isEmpty() && queryResponse.getResults().get(0).containsKey(metadataField.name())) {
            return Optional.of((String) queryResponse.getResults().get(0).getFirstValue(metadataField.name()));
        }

        return Optional.empty();
    }

    // Retrieves all the available factors stored in the Solr scxa-analytics collection for a particular cell
    private SingleCellAnalyticsSchemaField[] getFactorFieldNames(String experimentAccession, String cellId) {
        Map<String, Collection<Object>> queryResult = getCellQueryResultForMultiValueFields(
                experimentAccession,
                cellId,
                SingleCellAnalyticsCollectionProxy.FACTORS);

        return queryResult.getOrDefault(SingleCellAnalyticsCollectionProxy.FACTORS.name(), Collections.emptyList())
                .stream()
                .filter(factor -> !factor.toString().equalsIgnoreCase("single_cell_identifier"))
                .map(factor -> SingleCellAnalyticsCollectionProxy.factorAsSchemaField(factor.toString()))
                .toArray(SingleCellAnalyticsSchemaField[]::new);
    }

    // This returns Solr query results for a list of multi-value fields of interest
    private ImmutableMap<String, Collection<Object>> getCellQueryResultForMultiValueFields(
            String experimentAccession, String cellId, SingleCellAnalyticsSchemaField... fieldsOfInterest) {

        SolrQueryBuilder<SingleCellAnalyticsCollectionProxy> solrQueryBuilder =
                new SolrQueryBuilder<SingleCellAnalyticsCollectionProxy>()
                        .addFilterFieldByTerm(SingleCellAnalyticsCollectionProxy.EXPERIMENT_ACCESSION, experimentAccession)
                        .addQueryFieldByTerm(SingleCellAnalyticsCollectionProxy.CELL_ID, cellId)
                        .setFieldList(fieldsOfInterest)
                        .setRows(1);

        QueryResponse queryResponse = this.singleCellAnalyticsCollectionProxy.query(solrQueryBuilder);

        if(!queryResponse.getResults().isEmpty()) {
            // The map created by getFieldValuesMap has, for a reason I can’t grasp, quite a few unsupported
            // operations. Among them entrySet, which is the basis for all streaming methods in maps and forEach D:
            SolrDocument solrDocument = queryResponse.getResults().get(0);
            return solrDocument.getFieldNames().stream().collect(toImmutableMap(
                    Function.identity(),
                    solrDocument::getFieldValues));
        }

        return ImmutableMap.of();

    }

}
