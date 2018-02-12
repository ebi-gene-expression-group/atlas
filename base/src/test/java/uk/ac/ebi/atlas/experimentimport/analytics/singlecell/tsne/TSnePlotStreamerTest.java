package uk.ac.ebi.atlas.experimentimport.analytics.singlecell.tsne;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import uk.ac.ebi.atlas.commons.readers.TsvReader;
import uk.ac.ebi.atlas.model.resource.AtlasResource;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TSnePlotStreamerTest {

    private static final int STREAM_MAX_SIZE = 1000;

    @Mock
    private TsvReader tsvReaderMock1;

    @Mock
    private TsvReader tsvReaderMock5;

    @Mock
    private TsvReader tsvReaderMock10;

    @Mock
    private AtlasResource<TsvReader> atlasResourceTsvReaderMock1;

    @Mock
    private AtlasResource<TsvReader> atlasResourceTsvReaderMock5;

    @Mock
    private AtlasResource<TsvReader> atlasResourceTsvReaderMock10;

    private Map<Integer, AtlasResource<TsvReader>> tSnePlotsResource;


    private TSnePlotStreamer subject;

    @Before
    public void setUp() {
        when(atlasResourceTsvReaderMock1.get()).thenReturn(tsvReaderMock1);
        when(atlasResourceTsvReaderMock5.get()).thenReturn(tsvReaderMock5);
        when(atlasResourceTsvReaderMock10.get()).thenReturn(tsvReaderMock10);

        tSnePlotsResource =
                ImmutableMap.of(
                        1, atlasResourceTsvReaderMock1,
                        5, atlasResourceTsvReaderMock5,
                        10, atlasResourceTsvReaderMock10);

        subject = new TSnePlotStreamer(tSnePlotsResource);
    }

    @Test
    public void returnsAllPerplexities() {
        assertThat(subject.availablePerplexities()).containsExactly(1, 5, 10);
    }

    @Test
    public void stream() {
        List<TSnePoint> tSnePlot1 = randomTSnePoints(STREAM_MAX_SIZE);
        List<TSnePoint> tSnePlot5 = randomTSnePoints(STREAM_MAX_SIZE);
        List<TSnePoint> tSnePlot10 = randomTSnePoints(STREAM_MAX_SIZE);

        when(tsvReaderMock1.stream()).thenReturn(Stream.of(toTsv(tSnePlot1)));
        when(tsvReaderMock5.stream()).thenReturn(Stream.of(toTsv(tSnePlot5)));
        when(tsvReaderMock10.stream()).thenReturn(Stream.of(toTsv(tSnePlot10)));

        assertThat(subject.stream(1).collect(Collectors.toList())).containsExactlyElementsOf(tSnePlot1);
        assertThat(subject.stream(5).collect(Collectors.toList())).containsExactlyElementsOf(tSnePlot5);
        assertThat(subject.stream(10).collect(Collectors.toList())).containsExactlyElementsOf(tSnePlot10);
    }

    @Test
    public void headerLineIsSkipped() {
        List<TSnePoint> tSnePlot1 = randomTSnePoints(STREAM_MAX_SIZE);
        List<TSnePoint> tSnePlot5 = randomTSnePoints(STREAM_MAX_SIZE);
        List<TSnePoint> tSnePlot10 = randomTSnePoints(STREAM_MAX_SIZE);

        String[][] tSnePlot1Tsv = toTsv(tSnePlot1);
        String[][] tSnePlot5Tsv = toTsv(tSnePlot5);
        String[][] tSnePlot10Tsv = toTsv(tSnePlot10);

        assertThat(tSnePlot1Tsv.length).isEqualTo(tSnePlot1.size() + 1);
        assertThat(tSnePlot5Tsv.length).isEqualTo(tSnePlot5.size() + 1);
        assertThat(tSnePlot10Tsv.length).isEqualTo(tSnePlot10.size() + 1);

        when(tsvReaderMock1.stream()).thenReturn(Stream.of(tSnePlot1Tsv));
        when(tsvReaderMock5.stream()).thenReturn(Stream.of(tSnePlot5Tsv));
        when(tsvReaderMock10.stream()).thenReturn(Stream.of(tSnePlot10Tsv));

        assertThat(subject.stream(1).collect(Collectors.toList())).hasSize(tSnePlot1.size());
        assertThat(subject.stream(5).collect(Collectors.toList())).hasSize(tSnePlot5.size());
        assertThat(subject.stream(10).collect(Collectors.toList())).hasSize(tSnePlot10.size());
    }

    // This and the next test add a bit more robustness if irap_tsne fails for some reason
    @Test
    public void worksWithFilesThatContainOnlyTheHeader() {
        List<TSnePoint> tSnePlot1 = Collections.emptyList();
        List<TSnePoint> tSnePlot5 = Collections.emptyList();
        List<TSnePoint> tSnePlot10 = Collections.emptyList();

        when(tsvReaderMock1.stream()).thenReturn(Stream.of(toTsv(tSnePlot1)));
        when(tsvReaderMock5.stream()).thenReturn(Stream.of(toTsv(tSnePlot5)));
        when(tsvReaderMock10.stream()).thenReturn(Stream.of(toTsv(tSnePlot10)));

        assertThat(subject.stream(1).collect(Collectors.toList())).isEmpty();
        assertThat(subject.stream(5).collect(Collectors.toList())).isEmpty();
        assertThat(subject.stream(10).collect(Collectors.toList())).isEmpty();
    }

    @Test
    public void worksWithEmptyFiles() {
        when(tsvReaderMock1.stream()).thenReturn(Stream.empty());
        when(tsvReaderMock5.stream()).thenReturn(Stream.empty());
        when(tsvReaderMock10.stream()).thenReturn(Stream.empty());

        assertThat(subject.stream(1).collect(Collectors.toList())).isEmpty();
        assertThat(subject.stream(5).collect(Collectors.toList())).isEmpty();
        assertThat(subject.stream(10).collect(Collectors.toList())).isEmpty();
    }

    @Test
    public void autoClosesUnderlyingReaders() {
        try(TSnePlotStreamer subject = new TSnePlotStreamer(tSnePlotsResource)) {
            // Use subject...
        }
        verify(tsvReaderMock1).close();
        verify(tsvReaderMock5).close();
        verify(tsvReaderMock10).close();
    }

    static List<TSnePoint> randomTSnePoints(int maxSize) {
        int size = ThreadLocalRandom.current().nextInt(0, maxSize);

        HashSet<String> cellIds = new HashSet<>(size);
        while (cellIds.size() < size) {
            cellIds.add(randomAlphanumeric(20));
        }

        return cellIds.stream()
                .map(cellId ->
                                TSnePoint.create(
                                        ThreadLocalRandom.current().nextDouble(-100.0, 100.0),
                                        ThreadLocalRandom.current().nextDouble(-100.0, 100.0),
                                        cellId))
                .collect(Collectors.toList());
    }

    static private String[][] toTsv(List<TSnePoint> tSnePoints) {
        return Stream.concat(
                Stream.<String[]>of(new String[] {"tSNE_1", "tSNE_2", "Label"}),
                tSnePoints.stream()
                        .map(
                                tSnePoint -> new String[] {
                                        Double.toString(tSnePoint.x()),
                                        Double.toString(tSnePoint.y()),
                                        tSnePoint.name() }))
                .toArray(String[][]::new);
    }
}