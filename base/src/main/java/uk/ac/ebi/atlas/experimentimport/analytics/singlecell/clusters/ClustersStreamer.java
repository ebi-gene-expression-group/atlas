package uk.ac.ebi.atlas.experimentimport.analytics.singlecell.clusters;

import com.google.common.collect.Streams;
import org.apache.commons.lang3.tuple.Pair;
import uk.ac.ebi.atlas.commons.readers.TsvStreamer;
import uk.ac.ebi.atlas.model.resource.AtlasResource;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Arrays;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;

// Reads a clusters TSV file and streams pairs of k -> {(cell ID, cluster ID), (cell ID, cluster ID)...}, one per line
public class ClustersStreamer implements AutoCloseable, Supplier<Stream<Pair<Integer, Set<Pair<String, Integer>>>>> {
    // Column 0 is reserved for the sel.K parameter
    private final static int K_INDEX = 1;
    private final static int CELL_IDS_INDEX = 2;

    private final TsvStreamer clustersTsvStreamer;
    private final String[] cellIds;

    public ClustersStreamer(AtlasResource<TsvStreamer> clustersTsvStreamerResource) {
        this.clustersTsvStreamer = clustersTsvStreamerResource.get();

        cellIds = clustersTsvStreamer.get()
                .findFirst()
                .map(line -> Arrays.copyOfRange(line, CELL_IDS_INDEX, line.length))
                .orElseThrow(() -> new UncheckedIOException(new IOException("Blah!")));
    }

    @Override
    public Stream<Pair<Integer, Set<Pair<String, Integer>>>> get() {
        return clustersTsvStreamer.get()
                .skip(1)
                .map(
                        line ->
                                Pair.of(
                                        Integer.parseInt(line[K_INDEX]),
                                        pairCellIdsToClusters(Arrays.copyOfRange(line, CELL_IDS_INDEX, line.length))));
    }

    private Set<Pair<String, Integer>> pairCellIdsToClusters(String[] line) {
        return Streams.zip(
                Arrays.stream(cellIds),
                Arrays.stream(line).map(Integer::parseInt),
                Pair::of)
                .collect(toSet());
    }

    @Override
    public void close() {
        clustersTsvStreamer.close();
    }
}
