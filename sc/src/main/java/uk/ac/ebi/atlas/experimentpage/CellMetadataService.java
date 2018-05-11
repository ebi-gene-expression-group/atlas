package uk.ac.ebi.atlas.experimentpage;

import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.springframework.stereotype.Component;
import uk.ac.ebi.atlas.experimentimport.idf.IdfParser;
import uk.ac.ebi.atlas.experimentimport.idf.IdfParserOutput;
import uk.ac.ebi.atlas.solr.cloud.SolrCloudCollectionProxyFactory;
import uk.ac.ebi.atlas.solr.cloud.fullanalytics.SingleCellAnalyticsCollectionProxy;
import uk.ac.ebi.atlas.solr.cloud.search.SolrQueryBuilder;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static uk.ac.ebi.atlas.solr.cloud.fullanalytics.SingleCellAnalyticsCollectionProxy.SingleCellAnalyticsSchemaField;

@Component
public class CellMetadataService {

    private IdfParser idfParser;
    private SingleCellAnalyticsCollectionProxy singleCellAnalyticsCollectionProxy;

    public CellMetadataService(IdfParser idfParser, SolrCloudCollectionProxyFactory solrCloudCollectionProxyFactory) {
        this.idfParser = idfParser;

        this.singleCellAnalyticsCollectionProxy = solrCloudCollectionProxyFactory.createSingleCellAnalyticsCollectionProxy();
    }

    public Optional<String> getInferredCellType(String experimentAccession, String cellId) {
        return getMetadataFieldValueForCellId(SingleCellAnalyticsCollectionProxy.CHARACTERISTIC_INFERRED_CELL_TYPE, experimentAccession, cellId);
    }

    public Map<String, String> getFactors(String experimentAccession, String cellId) {
        Map<String, String> metadata = new HashMap<>();

        SingleCellAnalyticsSchemaField[] fields = getFactorFieldNames(experimentAccession, cellId);

        Map<String, Collection<Object>> results = getCellQueryResultForMultiValueFields(experimentAccession, cellId, fields);

        for(String factorId : results.keySet()) {
            String factorValue = StringUtils.join(results.get(factorId), ",");

            metadata.put(factorId, factorValue);
        }

        return metadata;
    }

    // This is not currently used but leaving in for now in case the logic is still useful
    public Map<String, String> getIdfFileAttributes(String experimentAccession, String cellId) {
        Map<String, String> attributes = new HashMap<>();

        IdfParserOutput idfParserOutput = idfParser.parse(experimentAccession);

        if(!idfParserOutput.getMetadataFieldsOfInterest().isEmpty()) {

            SingleCellAnalyticsSchemaField[] attributeFields = idfParserOutput.getMetadataFieldsOfInterest()
                    .stream()
                    .map(attribute -> SingleCellAnalyticsCollectionProxy.characteristicAsSchemaField(attributeNameToFieldName((attribute))))
                    .toArray(SingleCellAnalyticsSchemaField[]::new);

            Map<String, Collection<Object>> results = getCellQueryResultForMultiValueFields(experimentAccession, cellId, attributeFields);

            for(String attributeId : results.keySet()) {
                String factorValue = StringUtils.join(results.get(attributeId), ",");

                attributes.put(attributeId, factorValue);
            }
        }

        return attributes;
    }

    // Converts Solr field names to human-friendly names (e.g. inferred_cell_type => Inferred cell type)
    public String factorFieldNameToDisplayName(String factorFieldName) {
        String displayName = factorFieldName.replace("factor_", "").replace("_", " ");

        return StringUtils.capitalize(displayName);
    }

    // Converts strings from .idf file to Solr schema field names (e.g. FACS marker => facs_marker)
    private String attributeNameToFieldName(String attributeName) {
        return attributeName.trim().toLowerCase().replace(" ", "_");
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
                new SingleCellAnalyticsSchemaField[] {SingleCellAnalyticsCollectionProxy.FACTORS});

        return queryResult.getOrDefault(SingleCellAnalyticsCollectionProxy.FACTORS.name(), Collections.emptyList())
                .stream()
                .filter(factor -> !factor.toString().equalsIgnoreCase("single_cell_identifier"))
                .map(factor -> SingleCellAnalyticsCollectionProxy.factorAsSchemaField(factor.toString()))
                .toArray(SingleCellAnalyticsSchemaField[]::new);
    }

    // This returns Solr query results for a list of multi-value fields of interest
    private Map<String, Collection<Object>> getCellQueryResultForMultiValueFields(String experimentAccession,
                                                                                  String cellId,
                                                                                  SingleCellAnalyticsSchemaField[] fieldsOfInterest) {
        Map<String, Collection<Object>> queryResult = new HashMap<>();

        SolrQueryBuilder<SingleCellAnalyticsCollectionProxy> solrQueryBuilder =
                new SolrQueryBuilder<SingleCellAnalyticsCollectionProxy>()
                        .addFilterFieldByTerm(SingleCellAnalyticsCollectionProxy.EXPERIMENT_ACCESSION, experimentAccession)
                        .addQueryFieldByTerm(SingleCellAnalyticsCollectionProxy.CELL_ID, cellId)
                        .setFieldList(fieldsOfInterest);

        QueryResponse queryResponse = this.singleCellAnalyticsCollectionProxy.query(solrQueryBuilder);

        if(!queryResponse.getResults().isEmpty()) {
            queryResult = queryResponse.getResults().get(0).getFieldValuesMap();
        }

        return queryResult;
    }

}
