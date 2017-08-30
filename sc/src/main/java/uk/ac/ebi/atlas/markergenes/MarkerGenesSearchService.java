package uk.ac.ebi.atlas.markergenes;

import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.http.client.utils.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.net.URISyntaxException;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Named
public class MarkerGenesSearchService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MarkerGenesSearchService.class);

    static final double DEFAULT_P_THRESHOLD = 0.85;

    private final MarkerGeneDao markerGeneDao;
    private final Gson gson = new Gson();

    @Inject
    public MarkerGenesSearchService(MarkerGeneDao markerGeneDao) {
        this.markerGeneDao = markerGeneDao;
    }

    public ImmutableList<ImmutablePair<MarkerGeneProfile, String>> searchMarkerGenesByGeneId(String geneId) {
        return ImmutableList.copyOf(
                markerGeneDao.fetchMarkerGenes(geneId, DEFAULT_P_THRESHOLD).stream()
                        .map(mgp -> Pair.of(mgp, buildUrl(mgp, geneId)))
                        .filter(pair -> pair.getRight().isPresent())
                        .map(pair -> ImmutablePair.of(pair.getLeft(), pair.getRight().get()))
                        .collect(toList()));
    }

    private Optional<String> buildUrl(MarkerGeneProfile markerGeneProfile, String geneId) {
        try {
            return Optional.of(
                    new URIBuilder()
                            .setPath("/gxa_sc/experiments/" + markerGeneProfile.experimentAccession())
                            .addParameter("geneId", geneId)
                            .addParameter("k", Integer.toString(markerGeneProfile.k()))
                            .addParameter("clusterId", gson.toJson(markerGeneProfile.clusters().stream().map(Pair::getLeft).collect(toList())))
                            .build()
                            .toString());
        } catch (URISyntaxException e) {
            LOGGER.error("Unable to build URL from markerGene {}", geneId);
            return Optional.empty();
        }
    }
}
