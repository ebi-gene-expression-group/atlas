package uk.ac.ebi.atlas.search;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.solr.common.util.SimpleOrderedMap;
import org.springframework.stereotype.Component;
import uk.ac.ebi.atlas.experimentpage.ExperimentAttributesService;
import uk.ac.ebi.atlas.model.experiment.Experiment;
import uk.ac.ebi.atlas.model.experiment.baseline.Cell;
import uk.ac.ebi.atlas.solr.cloud.SolrCloudCollectionProxyFactory;
import uk.ac.ebi.atlas.solr.cloud.fullanalytics.SingleCellAnalyticsCollectionProxy;
import uk.ac.ebi.atlas.solr.cloud.search.SolrQueryBuilder;
import uk.ac.ebi.atlas.trader.ScxaExperimentTrader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static uk.ac.ebi.atlas.solr.cloud.fullanalytics.SingleCellAnalyticsCollectionProxy.CELL_ID;
import static uk.ac.ebi.atlas.solr.cloud.fullanalytics.SingleCellAnalyticsCollectionProxy.CHARACTERISTIC_INFERRED_CELL_TYPE;
import static uk.ac.ebi.atlas.solr.cloud.fullanalytics.SingleCellAnalyticsCollectionProxy.CHARACTERISTIC_ORGANISM_PART;
import static uk.ac.ebi.atlas.solr.cloud.fullanalytics.SingleCellAnalyticsCollectionProxy.EXPERIMENT_ACCESSION;
import static uk.ac.ebi.atlas.solr.cloud.fullanalytics.SingleCellAnalyticsCollectionProxy.SingleCellAnalyticsSchemaField;

@Component
public class GeneSearchService {
    private GeneSearchServiceDao geneSearchServiceDao;
    private SingleCellAnalyticsCollectionProxy singleCellAnalyticsCollectionProxy;
    private ScxaExperimentTrader experimentTrader;
    private ExperimentAttributesService experimentAttributesService;

    public GeneSearchService(GeneSearchServiceDao geneSearchServiceDao,
                             SolrCloudCollectionProxyFactory solrCloudCollectionProxyFactory,
                             ScxaExperimentTrader experimentTrader,
                             ExperimentAttributesService experimentAttributesService) {
        this.geneSearchServiceDao = geneSearchServiceDao;
        this.singleCellAnalyticsCollectionProxy = solrCloudCollectionProxyFactory.createSingleCellAnalyticsCollectionProxy();
        this.experimentTrader = experimentTrader;
        this.experimentAttributesService = experimentAttributesService;
    }

    public Map<String, List<String>> getCellIdsInExperiments(String geneId) {
        return geneSearchServiceDao.fetchCellIds(geneId);
    }

    public Map<String, Object> getExperimentInformation(String experimentAccession) {
        Experiment<Cell> experiment = experimentTrader.getPublicExperiment(experimentAccession);

        return experimentAttributesService.getAttributes(experiment);
    }

    // Returns inferred cell types and organism parts for each experiment accession
    public Map<String, Map<String, List<String>>> getFacets(List<String> cellIds) {
        List<SingleCellAnalyticsSchemaField> subFacetFields = Arrays.asList(CHARACTERISTIC_INFERRED_CELL_TYPE, CHARACTERISTIC_ORGANISM_PART);

        SolrQueryBuilder<SingleCellAnalyticsCollectionProxy> queryBuilder =
                new SolrQueryBuilder<SingleCellAnalyticsCollectionProxy>()
                        .addQueryFieldByTerm(CELL_ID, cellIds)
                        .setFacetField(EXPERIMENT_ACCESSION)
                        .setSubFacetList(subFacetFields.toArray(new SingleCellAnalyticsSchemaField[0]))
                        .setRows(0);

        ArrayList<SimpleOrderedMap> resultsByExperiment = (ArrayList<SimpleOrderedMap>) singleCellAnalyticsCollectionProxy.query(queryBuilder).getResponse().findRecursive("facets", EXPERIMENT_ACCESSION.name(), "buckets");

        return resultsByExperiment
                .stream()
                .collect(Collectors.toMap(
                        subFacetValues -> subFacetValues.get("val").toString(),
                        subFacetValues -> subFacetFields
                                .stream()
                                .map(SingleCellAnalyticsCollectionProxy.SingleCellAnalyticsSchemaField::name)
                                .collect(Collectors.toMap(
                                        subFacetField -> subFacetField,
                                        subFacetField -> getValuesForFacetField(subFacetValues, subFacetField)
                                ))
        ));
    }

    public Map<String, List<Pair<Integer, Integer>>> getMarkerGeneProfile(String geneId) {
        return geneSearchServiceDao.fetchKAndClusterIds(geneId);

    }

    private List<String> getValuesForFacetField(SimpleOrderedMap map, String facetField) {
        ArrayList<SimpleOrderedMap> results = (ArrayList<SimpleOrderedMap>) map.findRecursive(facetField, "buckets");

        return results
                .stream()
                .map(x -> x.get("val").toString())
                .collect(Collectors.toList());
    }
}
