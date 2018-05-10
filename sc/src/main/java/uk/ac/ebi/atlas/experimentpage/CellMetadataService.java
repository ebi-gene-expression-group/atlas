package uk.ac.ebi.atlas.experimentpage;

import org.apache.solr.client.solrj.response.QueryResponse;
import org.springframework.stereotype.Component;
import uk.ac.ebi.atlas.experimentimport.idf.IdfParser;
import uk.ac.ebi.atlas.experimentimport.idf.IdfParserOutput;
import uk.ac.ebi.atlas.solr.cloud.SchemaField;
import uk.ac.ebi.atlas.solr.cloud.SolrCloudCollectionProxyFactory;
import uk.ac.ebi.atlas.solr.cloud.fullanalytics.SingleCellAnalyticsCollectionProxy;
import uk.ac.ebi.atlas.solr.cloud.search.SolrQueryBuilder;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    public Map<String, String> getAttributeFromIdfFile(String experimentAccession, String cellId) {
        Map<String, String> result = new HashMap<>();

        IdfParserOutput idfParserOutput = idfParser.parse(experimentAccession);

        if(!idfParserOutput.getMetadataFieldsOfInterest().isEmpty()) {
            for(String metadataFieldName : idfParserOutput.getMetadataFieldsOfInterest()) {
                Optional<String> metadataValue = getCharacteristicValueForCellId(metadataFieldName, experimentAccession, cellId);

                metadataValue.ifPresent(s -> result.put(metadataFieldName, s));
            }
        }

        return result;
    }

    private Optional<String> getCharacteristicValueForCellId(String metadataFieldName, String experimentAccession, String cellId) {
        return getMetadataFieldValueForCellId(SingleCellAnalyticsCollectionProxy.characteristicAsSchemaField(metadataFieldName), experimentAccession, cellId);
    }

    private Optional<String> getFactorValueForCellId(String metadataFieldName, String experimentAccession, String cellId) {
        return getMetadataFieldValueForCellId(SingleCellAnalyticsCollectionProxy.factorAsSchemaField(metadataFieldName), experimentAccession, cellId);
    }

    private List<String> getMetadataFieldValuesForCellId(SchemaField<SingleCellAnalyticsCollectionProxy> metadataFields, String experimentAccession, String cellId) {
        // TODO

        return Collections.emptyList();
    }

    private Optional<String> getMetadataFieldValueForCellId(SchemaField<SingleCellAnalyticsCollectionProxy> metadataField, String experimentAccession, String cellId) {
        SolrQueryBuilder<SingleCellAnalyticsCollectionProxy> solrQueryBuilder =
                new SolrQueryBuilder<SingleCellAnalyticsCollectionProxy>()
                        .addFilterFieldByTerm(SingleCellAnalyticsCollectionProxy.EXPERIMENT_ACCESSION, experimentAccession)
                        .addQueryFieldByTerm(SingleCellAnalyticsCollectionProxy.CELL_ID, cellId)
                        .setFieldList(metadataField);
        QueryResponse queryResponse = this.singleCellAnalyticsCollectionProxy.query(solrQueryBuilder);

        if(!queryResponse.getResults().isEmpty() && queryResponse.getResults().get(0).containsKey(metadataField.name())) {
            return Optional.of((String) queryResponse.getResults().get(0).getFirstValue(metadataField.name()));
        }

        return Optional.empty();
    }
}
