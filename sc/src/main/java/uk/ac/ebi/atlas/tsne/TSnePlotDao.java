package uk.ac.ebi.atlas.tsne;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import org.apache.commons.io.IOUtils;
import uk.ac.ebi.atlas.experimentpage.JsonSingleCellExperimentController;

import javax.inject.Named;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@Named
public class TSnePlotDao {
    private final Gson gson;

    public TSnePlotDao() {
        gson = new GsonBuilder().registerTypeAdapter(TSnePoint.class, TSnePoint.getGsonTypeAdapter()).create();
    }

    public ImmutableMap<String, TSnePoint> fetchTSnePlotPoints(String experimentAccession) {
        try {
            return hardCodedTSneCoordinates();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private ImmutableMap<String, TSnePoint> hardCodedTSneCoordinates() throws IOException {
        // TODO Hard-coded until we have real data
        String coordinatesJson = IOUtils.readLines(
                JsonSingleCellExperimentController.class.getResourceAsStream("tsne-coordinates.json"),
                StandardCharsets.UTF_8)
                .stream().collect(Collectors.joining(""));

        ImmutableMap.Builder<String, TSnePoint> pointsMapBuilder = ImmutableMap.builder();
        new JsonParser().parse(coordinatesJson)
                .getAsJsonArray()
                .iterator()
                .forEachRemaining(jsonElement -> {
                    TSnePoint point = gson.fromJson(jsonElement, TSnePoint.class);
                    pointsMapBuilder.put(point.name(), point);
                });

        return pointsMapBuilder.build();
    }
}
