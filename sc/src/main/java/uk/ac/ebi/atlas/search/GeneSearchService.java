package uk.ac.ebi.atlas.search;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;
import static uk.ac.ebi.atlas.solr.cloud.collections.SingleCellAnalyticsCollectionProxy.CHARACTERISTIC_INFERRED_CELL_TYPE;
import static uk.ac.ebi.atlas.solr.cloud.collections.SingleCellAnalyticsCollectionProxy.CHARACTERISTIC_ORGANISM_PART;
import static uk.ac.ebi.atlas.solr.cloud.collections.SingleCellAnalyticsCollectionProxy.CHARACTERISTIC_SPECIES;

@Component
public class GeneSearchService {
    private GeneSearchServiceDao geneSearchServiceDao;

    public GeneSearchService(GeneSearchServiceDao geneSearchServiceDao) {
        this.geneSearchServiceDao = geneSearchServiceDao;

    }

    // Map<Gene ID, Map<Experiment accession, List<Cell IDs>>>
    public Map<String, Map<String, List<String>>> getCellIdsInExperiments(String... geneIds) {
        return fetchInParallel(
                ImmutableSet.copyOf(geneIds),
                geneId -> geneSearchServiceDao.fetchCellIds(geneId));
    }

    // Returns inferred cell types and organism parts for each experiment accession
    public Map<String, Map<String, List<String>>> getFacets(List<String> cellIds) {
        return geneSearchServiceDao.getFacets(
                cellIds,
                CHARACTERISTIC_INFERRED_CELL_TYPE, CHARACTERISTIC_ORGANISM_PART, CHARACTERISTIC_SPECIES);
    }

    // Map<Gene ID, Map<Experiment accession, Map<K, Cluster ID>>>
    public Map<String, Map<String, Map<Integer, List<Integer>>>> getMarkerGeneProfile(String... geneIds) {
        return fetchInParallel(
                ImmutableSet.copyOf(geneIds),
                geneId -> geneSearchServiceDao.fetchKAndClusterIds(geneId));
    }

    private <T> Map<String, T> fetchInParallel(Set<String> geneIds, Function<String, T> geneIdInfoProvider) {
        // If this becomes a resource hog, consider having the pool as a member of the class and reuse it every time
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        try {

            return
                    forkJoinPool.submit(
                            () -> geneIds.stream().parallel()
                                    .map(geneId -> ImmutableMap.of(geneId, geneIdInfoProvider.apply(geneId)))
                                    .map(Map::entrySet)
                                    .flatMap(Set::stream)
                                    .collect(toMap(Map.Entry::getKey, Map.Entry::getValue))
                    ).get();

        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        } finally {
            forkJoinPool.shutdown();
        }
    }
}
