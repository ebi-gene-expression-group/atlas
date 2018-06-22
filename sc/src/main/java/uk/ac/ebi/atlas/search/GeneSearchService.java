package uk.ac.ebi.atlas.search;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

import static uk.ac.ebi.atlas.solr.cloud.fullanalytics.SingleCellAnalyticsCollectionProxy.CHARACTERISTIC_INFERRED_CELL_TYPE;
import static uk.ac.ebi.atlas.solr.cloud.fullanalytics.SingleCellAnalyticsCollectionProxy.CHARACTERISTIC_ORGANISM_PART;
import static uk.ac.ebi.atlas.solr.cloud.fullanalytics.SingleCellAnalyticsCollectionProxy.CHARACTERISTIC_SPECIES;

@Component
public class GeneSearchService {
    private GeneSearchDao geneSearchDao;


    public GeneSearchService(GeneSearchDao geneSearchDao) {
        this.geneSearchDao = geneSearchDao;

    }

    public Map<String, List<String>> getCellIdsInExperiments(String geneId) {
        return geneSearchDao.fetchCellIds(geneId);
    }

    // Returns inferred cell types and organism parts for each experiment accession
    public Map<String, Map<String, List<String>>> getFacets(List<String> cellIds) {
        return geneSearchDao.getFacets(cellIds, CHARACTERISTIC_INFERRED_CELL_TYPE, CHARACTERISTIC_ORGANISM_PART, CHARACTERISTIC_SPECIES);
    }

    public Map<String, Map<Integer, List<Integer>>> getMarkerGeneProfile(String geneId) {
        return geneSearchDao.fetchKAndClusterIds(geneId);
    }
}
