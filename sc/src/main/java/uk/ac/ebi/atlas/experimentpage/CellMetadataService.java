package uk.ac.ebi.atlas.experimentpage;

import org.apache.solr.client.solrj.response.QueryResponse;
import org.springframework.stereotype.Component;
import uk.ac.ebi.atlas.experimentimport.idf.IdfParser;
import uk.ac.ebi.atlas.experimentimport.idf.IdfParserOutput;
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
        SolrQueryBuilder<SingleCellAnalyticsCollectionProxy> solrQueryBuilder =
                new SolrQueryBuilder<SingleCellAnalyticsCollectionProxy>()
                        .addFilterFieldByTerm(SingleCellAnalyticsCollectionProxy.EXPERIMENT_ACCESSION, experimentAccession)
                        .addQueryFieldByTerm(SingleCellAnalyticsCollectionProxy.CELL_ID, cellId)
                        .setFieldList(SingleCellAnalyticsCollectionProxy.CHARACTERISTIC_INFERRED_CELL_TYPE);
        QueryResponse queryResponse = this.singleCellAnalyticsCollectionProxy.query(solrQueryBuilder);

        if(!queryResponse.getResults().isEmpty() && queryResponse.getResults().get(0).containsKey(SingleCellAnalyticsCollectionProxy.CHARACTERISTIC_INFERRED_CELL_TYPE.name())) {
            return Optional.of((String) queryResponse.getResults().get(0).getFirstValue(SingleCellAnalyticsCollectionProxy.CHARACTERISTIC_INFERRED_CELL_TYPE.name()));
        }

        return Optional.empty();

    }

    public Optional<String> getAttributeFromIdfFile(String experimentAccession, String cellId) {
        IdfParserOutput idfParserOutput = idfParser.parse(experimentAccession);


        return Optional.empty();
    }
}
