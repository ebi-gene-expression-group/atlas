package uk.ac.ebi.atlas.search;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import org.springframework.stereotype.Component;
import uk.ac.ebi.atlas.experimentpage.TsnePlotSettingsService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
    private GeneSearchDao geneSearchDao;
    private TsnePlotSettingsService tsnePlotSettingsService;

    public GeneSearchService(GeneSearchDao geneSearchDao, TsnePlotSettingsService tsnePlotSettingsService) {
        this.geneSearchDao = geneSearchDao;
        this.tsnePlotSettingsService = tsnePlotSettingsService;

    }

    // Map<Gene ID, Map<Experiment accession, List<Cell IDs>>>
    public Map<String, Map<String, List<String>>> getCellIdsInExperiments(String... geneIds) {
        return fetchInParallel(
                ImmutableSet.copyOf(geneIds),
                geneId -> geneSearchDao.fetchCellIds(geneId));
    }

    // Returns inferred cell types and organism parts for each experiment accession
    public Map<String, Map<String, List<String>>> getFacets(List<String> cellIds) {
        return geneSearchDao.getFacets(
                cellIds,
                CHARACTERISTIC_INFERRED_CELL_TYPE, CHARACTERISTIC_ORGANISM_PART, CHARACTERISTIC_SPECIES);
    }

    // Map<Gene ID, Map<Experiment accession, Map<K, Cluster ID>>>
    public Map<String, Map<String, Map<Integer, List<Integer>>>> getMarkerGeneProfile(String... geneIds) {

        return fetchInParallel(
                ImmutableSet.copyOf(geneIds),
                geneId -> fetchClusterIDWithPreferredKAndMinPForGeneID(
                        geneSearchDao.fetchExperimentAccessionsWhereGeneIsMarker(geneId),
                        geneId));
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

    private Map<String, Map<Integer, List<Integer>>> fetchClusterIDWithPreferredKAndMinPForGeneID(
            List<String> experimentAccessions, String geneId) {
        Map<String, Map<Integer, List<Integer>>> result = new HashMap<>();

        for (String experimentAccession : experimentAccessions) {
            Optional<Integer> preferredK = tsnePlotSettingsService.getExpectedClusters(experimentAccession);
            Map<Integer, List<Integer>> clusterIDWithPreferredKAndMinP =
                    geneSearchDao.fetchClusterIdsWithPreferredKAndMinPForExperimentAccession(
                            geneId,
                            experimentAccession,
                            // If there’s no preferred k we use 0, which won’t match any row
                            preferredK.orElse(0));
            if (!clusterIDWithPreferredKAndMinP.isEmpty()) {
                result.put(experimentAccession, clusterIDWithPreferredKAndMinP);
            }
        }
        return result;

    }
}
