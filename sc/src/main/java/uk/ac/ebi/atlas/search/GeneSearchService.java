package uk.ac.ebi.atlas.search;

import org.springframework.stereotype.Component;
import uk.ac.ebi.atlas.experimentpage.ExperimentAttributesService;
import uk.ac.ebi.atlas.model.experiment.Experiment;
import uk.ac.ebi.atlas.model.experiment.baseline.Cell;
import uk.ac.ebi.atlas.solr.cloud.SolrCloudCollectionProxyFactory;
import uk.ac.ebi.atlas.solr.cloud.fullanalytics.SingleCellAnalyticsCollectionProxy;
import uk.ac.ebi.atlas.trader.ScxaExperimentTrader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public Map<String, String> getFacets(List<String> cellIds) {
        Map<String, String> result = new HashMap<>();

        //TODO Query Solr for facets

        return result;
    }

    public Map<String, Object> getMarkerGeneProfile(String geneId) {
        Map<String, Object> result = new HashMap<>();

        // TODO Use geneSearchServiceDao to query for pairs of (k, cluster_id) for every experiment accession given a gene ID

        return result;
    }
}
