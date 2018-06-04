package uk.ac.ebi.atlas.search;

import org.springframework.stereotype.Component;
import uk.ac.ebi.atlas.experimentpage.ExperimentAttributesService;
import uk.ac.ebi.atlas.model.experiment.Experiment;
import uk.ac.ebi.atlas.model.experiment.baseline.Cell;
import uk.ac.ebi.atlas.trader.ScxaExperimentTrader;

import java.util.List;
import java.util.Map;

import static uk.ac.ebi.atlas.solr.cloud.fullanalytics.SingleCellAnalyticsCollectionProxy.CHARACTERISTIC_INFERRED_CELL_TYPE;
import static uk.ac.ebi.atlas.solr.cloud.fullanalytics.SingleCellAnalyticsCollectionProxy.CHARACTERISTIC_ORGANISM_PART;

@Component
public class GeneSearchService {
    private GeneSearchServiceDao geneSearchServiceDao;
    private ScxaExperimentTrader experimentTrader;
    private ExperimentAttributesService experimentAttributesService;

    public GeneSearchService(GeneSearchServiceDao geneSearchServiceDao,
                             ScxaExperimentTrader experimentTrader,
                             ExperimentAttributesService experimentAttributesService) {
        this.geneSearchServiceDao = geneSearchServiceDao;
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
        return geneSearchServiceDao.getFacets(cellIds, CHARACTERISTIC_INFERRED_CELL_TYPE, CHARACTERISTIC_ORGANISM_PART);
    }

    public Map<String, Map<Integer, List<Integer>>> getMarkerGeneProfile(String geneId) {
        return geneSearchServiceDao.fetchKAndClusterIds(geneId);
    }
}
