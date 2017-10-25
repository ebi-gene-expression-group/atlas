package uk.ac.ebi.atlas.tsne;

import com.google.common.collect.ImmutableMap;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GeneExpressionDaoTest {
    private final static int NUM_CLUSTERS = 4;
    private final static int NUM_POINTS_PER_CLUSTER = 1000;
    private final static String EXPERIMENT_ACCESSION = "E-FOOBAR";

    @Mock
    TSnePlotDao tSnePlotDaoMock;

    GeneExpressionDao subject;

    private String cellName(int i) {
        return "Cell " + Integer.toString(i);
    }

    private ImmutableMap<String, TSnePoint> randomTSnePlotGenerator(int numPoints) {
        return ImmutableMap.copyOf(IntStream.range(0, numPoints).boxed().collect(Collectors.toMap(
                this::cellName,
                i -> TSnePoint.create(
                        ThreadLocalRandom.current().nextDouble(), ThreadLocalRandom.current().nextDouble(),
                        cellName(i)))));
    }

    @Before
    public void setUp() throws Exception {
        ImmutableMap<String, TSnePoint> tSnePlot = randomTSnePlotGenerator(NUM_CLUSTERS * NUM_POINTS_PER_CLUSTER);
        when(tSnePlotDaoMock.fetchTSnePlotPoints(EXPERIMENT_ACCESSION)).thenReturn(tSnePlot);
        subject = new GeneExpressionDao(tSnePlotDaoMock);
    }

    @Test
    public void fetchGeneExpressionWithEmptyId() throws Exception {
        subject.fetchGeneExpression(EXPERIMENT_ACCESSION, "").values().forEach(
                expression -> assertThat(expression, is(0.0))
        );
    }

}