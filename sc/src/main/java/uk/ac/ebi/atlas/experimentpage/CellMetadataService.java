package uk.ac.ebi.atlas.experimentpage;

import org.apache.solr.client.solrj.response.QueryResponse;
import org.springframework.stereotype.Component;
import uk.ac.ebi.atlas.experimentimport.idf.IdfParser;
import uk.ac.ebi.atlas.experimentimport.idf.IdfParserOutput;
import uk.ac.ebi.atlas.solr.cloud.SchemaField;
import uk.ac.ebi.atlas.solr.cloud.SolrCloudCollectionProxyFactory;
import uk.ac.ebi.atlas.solr.cloud.fullanalytics.SingleCellAnalyticsCollectionProxy;
import uk.ac.ebi.atlas.solr.cloud.search.SolrQueryBuilder;

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

    public Optional<String> getAttributeFromIdfFile(String experimentAccession, String cellId) {
        IdfParserOutput idfParserOutput = idfParser.parse(experimentAccession);

        return Optional.empty();
    }

    private Optional<String> getCharacteristicValueForCellId(String metadataFieldName, String experimentAccession, String cellId) {
        return getMetadataFieldValueForCellId(SingleCellAnalyticsCollectionProxy.characteristicAsSchemaField(metadataFieldName), experimentAccession, cellId);
    }

    private Optional<String> getFactorValueForCellId(String metadataFieldName, String experimentAccession, String cellId) {
        return getMetadataFieldValueForCellId(SingleCellAnalyticsCollectionProxy.factorAsSchemaField(metadataFieldName), experimentAccession, cellId);
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
