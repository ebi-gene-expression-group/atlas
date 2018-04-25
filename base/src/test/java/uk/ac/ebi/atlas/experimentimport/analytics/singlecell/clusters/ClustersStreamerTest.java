package uk.ac.ebi.atlas.experimentimport.analytics.singlecell.clusters;

import com.google.common.collect.Streams;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.ac.ebi.atlas.RandomDataTestUtils;
import uk.ac.ebi.atlas.commons.readers.TsvStreamer;
import uk.ac.ebi.atlas.model.resource.AtlasResource;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClustersStreamerTest {
    private static final int NUMBER_OF_CELLS = 1000;
    private static final int FROM_K = 7;
    private static final int TO_K = 20;

    @Mock
    private AtlasResource<TsvStreamer> tsvStreamerResourceMock;

    @Mock
    private TsvStreamer tsvStreamerMock;

    private List<String[]> clustersTsv;

    @BeforeEach
    void setUp() {
        when(tsvStreamerResourceMock.get()).thenReturn(tsvStreamerMock);

        clustersTsv = RandomDataTestUtils.getRandomClusters(FROM_K, TO_K, NUMBER_OF_CELLS);
        when(tsvStreamerMock.get()).thenReturn(clustersTsv.stream());
    }

    @Test
    @DisplayName("All entries are properly read")
    void testGetResults() {
        ClustersStreamer subject = new ClustersStreamer(tsvStreamerResourceMock);

        // Remember that in production code you need to use with try-with-resources!
        List<Pair<Integer, Set<Pair<String, Integer>>>> results = subject.stream().collect(toList());

        for (String[] line : clustersTsv.subList(1, clustersTsv.size())) {
            assertThat(results)
                    .filteredOn(pair -> pair.getLeft() == Integer.parseInt(line[1]))
                    .flatExtracting("right")
                    .containsAll(pairCellIdsToClusterIds(clustersTsv.get(0), line))
                    .hasSize(clustersTsv.get(0).length - 2);
        }
    }

    @Test
    @DisplayName("Backing reader is autoclosed")
    void testAutocloses() {
        when(tsvStreamerMock.get()).thenReturn(RandomDataTestUtils.getRandomClusters(FROM_K, TO_K, NUMBER_OF_CELLS).stream());
        try(ClustersStreamer subject = new ClustersStreamer(tsvStreamerResourceMock)) {
            // Use subject...
        }
        verify(tsvStreamerMock).close();
    }

    @Test
    @DisplayName("An empty file produces nothing")
    void testEmptyFile() {
        // The TSV file is first opened to read the header, and a second time for the contents
        when(tsvStreamerMock.get()).thenReturn(Stream.empty()).thenReturn(Stream.empty());
        ClustersStreamer subject = new ClustersStreamer(tsvStreamerResourceMock);
        assertThat(subject.stream().collect(toList())).isEmpty();
    }

    private List<Pair<String, Integer>> pairCellIdsToClusterIds(String[] header, String[] line) {
        return Streams.zip(
                Arrays.stream(Arrays.copyOfRange(header, 2, header.length)),
                Arrays.stream(Arrays.copyOfRange(line, 2, line.length)).map(Integer::parseInt),
                Pair::of)
                .collect(toList());
    }
}