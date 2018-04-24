package uk.ac.ebi.atlas.experimentimport.analytics.singlecell.clusters;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Streams;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.OngoingStubbing;
import uk.ac.ebi.atlas.commons.readers.TsvStreamer;
import uk.ac.ebi.atlas.model.resource.AtlasResource;

import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClustersStreamerTest {
    private static final int NUMBER_OF_CELLS = 10;

    private static final String[] TSV_LINE_1 =
            ImmutableList.<String>builder()
                    .add("sel.K", "K")
                    .addAll(randomSingleCellRnaSeqRunIds(NUMBER_OF_CELLS)).build().toArray(new String[0]);
    private static final String[] TSV_LINE_2 =
            ImmutableList.<String>builder()
                    .add("FALSE")
                    .addAll(randomClusters(4, NUMBER_OF_CELLS)).build().toArray(new String[0]);
    private static final String[] TSV_LINE_3 =
            ImmutableList.<String>builder()
                    .add("FALSE")
                    .addAll(randomClusters(5, NUMBER_OF_CELLS)).build().toArray(new String[0]);
    private static final String[] TSV_LINE_4 =
            ImmutableList.<String>builder()
                    .add("FALSE")
                    .addAll(randomClusters(6, NUMBER_OF_CELLS)).build().toArray(new String[0]);

    @Mock
    private AtlasResource<TsvStreamer> clustersTsvStreamerResourceMock;

    @Mock
    private TsvStreamer clustersTsvStreamerMock;


    @BeforeEach
    void setUp() {
        when(clustersTsvStreamerResourceMock.get()).thenReturn(clustersTsvStreamerMock);
    }

    @Test
    @DisplayName("All entries are properly read")
    void testGetResults() {
        // The TSV file is first opened to read the header, and a second time for the contents
        when(clustersTsvStreamerMock.get())
                .thenReturn(Stream.of(TSV_LINE_1, TSV_LINE_2, TSV_LINE_3, TSV_LINE_4))
                .thenReturn(Stream.of(TSV_LINE_1, TSV_LINE_2, TSV_LINE_3, TSV_LINE_4));
        ClustersStreamer subject = new ClustersStreamer(clustersTsvStreamerResourceMock);

        // Remember that in production code you need to use with try-with-resources!
        List<Pair<Integer, Set<Pair<String, Integer>>>> results = subject.get().collect(toList());

        for (String[] line : ImmutableList.of(TSV_LINE_2, TSV_LINE_3, TSV_LINE_4)) {
            assertThat(results)
                    .filteredOn(pair -> pair.getLeft() == Integer.parseInt(line[1]))
                    .flatExtracting("right")
                    .containsExactlyInAnyOrder(
                            Stream.of(pairClusterIdsToCellIds(line)).flatMap(identity()).toArray(Pair[]::new));
        }
    }

    @Test
    @DisplayName("Backing reader is autoclosed")
    void testAutocloses() {
        when(clustersTsvStreamerMock.get()).thenReturn(Stream.of(TSV_LINE_1, TSV_LINE_2, TSV_LINE_3, TSV_LINE_4));
        try(ClustersStreamer subject = new ClustersStreamer(clustersTsvStreamerResourceMock)) {
            // Use subject...
        }
        verify(clustersTsvStreamerMock).close();
    }

    @Test
    @DisplayName("An empty file produces nothing")
    void testEmptyFile() {
        // The TSV file is first opened to read the header, and a second time for the contents
        when(clustersTsvStreamerMock.get()).thenReturn(Stream.empty()).thenReturn(Stream.empty());
        ClustersStreamer subject = new ClustersStreamer(clustersTsvStreamerResourceMock);
        assertThat(subject.get().collect(toList())).isEmpty();
    }

    private static Set<String> randomSingleCellRnaSeqRunIds(int n) {
        Set<String> runIds = new HashSet<>(n);
        while (runIds.size() < n) {
            runIds.add("SRR" + randomNumeric(1, 7));
        }
        return runIds;
    }

    private static List<String> randomClusters(int k, int n) {
        List<String> clusterIds = new ArrayList<>(n);
        clusterIds.add(Integer.toString(k));
        while (clusterIds.size() < n + 1) {
            clusterIds.add(Integer.toString(ThreadLocalRandom.current().nextInt(1, k + 1)));
        }
        return clusterIds;
    }

    private Stream<Pair<String, Integer>> pairClusterIdsToCellIds(String[] line) {
        return Streams.zip(
                Arrays.stream(Arrays.copyOfRange(TSV_LINE_1, 2, TSV_LINE_1.length)),
                Arrays.stream(Arrays.copyOfRange(line, 2, line.length)).map(Integer::parseInt),
                Pair::of);
    }
}