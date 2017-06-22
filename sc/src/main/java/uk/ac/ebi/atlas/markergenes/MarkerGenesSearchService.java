package uk.ac.ebi.atlas.markergenes;

import com.google.gson.Gson;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import org.apache.http.client.utils.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Named
public class MarkerGenesSearchService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MarkerGenesSearchService.class);

    private final MarkerGeneDao markerGeneDao;

    @Inject
    public MarkerGenesSearchService(MarkerGeneDao markerGeneDao) {
        this.markerGeneDao = markerGeneDao;
    }

    public List<Pair<MarkerGene, String>> searchMarkerGenesByGeneId(String geneId) {
        return aggregateClusterIds(markerGeneDao.fetchMarkerGenes(geneId)).stream()
                .map(markerGene -> Pair.of(markerGene, buildUrl(markerGene)))
                .filter(pair -> pair.getRight().isPresent())
                .map(pair -> Pair.of(pair.getLeft(), pair.getRight().get()))
                .collect(Collectors.toList());
    }

    private List<MarkerGene> aggregateClusterIds(Collection<MarkerGeneDto> markerGeneDtos) {
        return markerGeneDtos.stream()
                // We could group by experimentAccession and perplexity only as we expect to have a single gene ID here
                // but we can be a bit more defensive with very little extra effort
                .collect(Collectors.groupingBy(mgd -> Triple.of(mgd.geneId(), mgd.experimentAccession(), mgd.perplexity())))
                .entrySet()
                .stream()
                .map(entry ->
                        MarkerGene.create(
                                entry.getKey().getLeft(), entry.getKey().getMiddle(), entry.getKey().getRight(),
                                entry.getValue().stream()
                                        .map(mgd -> ImmutablePair.of(mgd.clusterId(), mgd.p()))
                                        .collect(Collectors.toList())))
                .collect(Collectors.toList());
    }

    private Optional<String> buildUrl(MarkerGene markerGene) {
        int[] clusterIds = markerGene.clusterIds().stream().mapToInt(Pair::getLeft).toArray();

        try {
            return Optional.of(
                    new URIBuilder()
                            .setPath("/gxa_sc/experiments/" + markerGene.experimentAccession())
                            .addParameter("k", Integer.toString(markerGene.perplexity()))
                            .addParameter("geneId", markerGene.geneId())
                            .addParameter("clusterId", new Gson().toJson(clusterIds))
                            .build()
                            .toString());
        } catch (URISyntaxException e) {
            LOGGER.error("Unable to build URL from markerGene {}", markerGene.toString());
            return Optional.empty();
        }
    }
}
