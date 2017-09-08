package uk.ac.ebi.atlas.tsne;

import com.google.common.collect.ImmutableMultimap;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.io.IOUtils;
import uk.ac.ebi.atlas.experimentpage.JsonSingleCellExperimentController;

import javax.inject.Named;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.stream.Collectors;

@Named
public class ClusterDao {
    public ImmutableMultimap<Integer, String> fetchClusters(String experimentAccession, int k) {
        try {
            return hardCodedClusters(k);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private ImmutableMultimap<Integer, String> hardCodedClusters(int k) throws IOException {
        String stringifiedJson = IOUtils.readLines(
                JsonSingleCellExperimentController.class.getResourceAsStream("tsne-clusters.json"),
                StandardCharsets.UTF_8)
                .stream().collect(Collectors.joining(""));

        JsonObject parsedJson =  new JsonParser().parse(stringifiedJson).getAsJsonObject();
        Optional<JsonArray> entryK = Optional.ofNullable(parsedJson.getAsJsonArray(Integer.toString(k)));

        ImmutableMultimap.Builder<Integer, String> clustersBuilder = ImmutableMultimap.builder();
        entryK.ifPresent(
                jsonElements -> jsonElements.iterator().forEachRemaining(
                        jsonElement ->
                                clustersBuilder.put(
                                        jsonElement.getAsJsonArray().get(1).getAsInt(),
                                        jsonElement.getAsJsonArray().get(0).getAsString())));

        return clustersBuilder.build();
    }
}
