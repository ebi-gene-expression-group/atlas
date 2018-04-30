package uk.ac.ebi.atlas.experimentimport.analytics.singlecell.clusters;

import com.google.common.collect.Streams;
import org.apache.commons.lang3.tuple.Pair;
import uk.ac.ebi.atlas.commons.readers.TsvStreamer;
import uk.ac.ebi.atlas.model.resource.AtlasResource;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toSet;

// Reads a clusters TSV file and streams pairs of k -> {(cell ID, cluster ID), (cell ID, cluster ID)...}, one per line
public class ClustersStreamer implements AutoCloseable {
    // Column 0 is reserved for the sel.K parameter
    private final static int K_INDEX = 1;
    private final static int CELL_IDS_INDEX = 2;

    private final TsvStreamer clustersTsvStreamer;
    private final Iterator<String[]> clustersTsvLinesIterator;
    private final String[] cellIds;

    public ClustersStreamer(AtlasResource<TsvStreamer> clustersTsvStreamerResource) {
        clustersTsvStreamer = clustersTsvStreamerResource.get();
        clustersTsvLinesIterator = clustersTsvStreamer.get().iterator();

        if (clustersTsvLinesIterator.hasNext()) {
            String[] headerLine = clustersTsvLinesIterator.next();
            cellIds = Arrays.copyOfRange(headerLine, CELL_IDS_INDEX, headerLine.length);
        } else {
            cellIds = new String[0];
        }
    }

    public Stream<Pair<Integer, Set<Pair<String, Integer>>>> stream() {
        Iterable<String[]> iterable = () -> clustersTsvLinesIterator;
        return StreamSupport.stream(iterable.spliterator(), false)
                .map(line ->
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
