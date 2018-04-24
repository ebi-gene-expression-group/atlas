package uk.ac.ebi.atlas.experimentimport.analytics.singlecell.tsne;

import uk.ac.ebi.atlas.commons.readers.TsvStreamer;
import uk.ac.ebi.atlas.experimentpage.baseline.tsne.TSnePoint;
import uk.ac.ebi.atlas.model.resource.AtlasResource;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TSnePlotStreamer implements AutoCloseable {

    private final Map<Integer, TsvStreamer> tSnePlotTsvs;
    private final Set<Integer> perplexities;

    public TSnePlotStreamer(Map<Integer, AtlasResource<TsvStreamer>> tSnePlotTsvs) {
        this.perplexities = tSnePlotTsvs.keySet();
        this.tSnePlotTsvs = tSnePlotTsvs.entrySet().stream().collect(Collectors.toMap(
               Map.Entry::getKey,
               entry -> entry.getValue().get()
        ));
    }

    public Stream<TSnePoint> stream(int perplexity) {
        return tSnePlotTsvs.get(perplexity)
                .get()
                .skip(1)    // Discard header
                .map(line -> TSnePoint.create(Double.parseDouble(line[0]), Double.parseDouble(line[1]), line[2]));
    }

    public Set<Integer> availablePerplexities() {
        return perplexities;
    }

    @Override
    public void close() {
        tSnePlotTsvs.values().forEach(TsvStreamer::close);
    }
}
