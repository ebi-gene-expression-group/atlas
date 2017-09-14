package uk.ac.ebi.atlas.tsne;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.TreeMultimap;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TSnePlotCollatorTest {
    private final static int NUM_CLUSTERS = 4;
    private final static int NUM_POINTS_PER_CLUSTER = 1000;
    private final static String EXPERIMENT_ACCESSION = "E-FOOBAR";
    private final static String GENE_ID = "ENSFOOBAR";

    @Mock
    TSnePlotDao tSnePlotDaoMock;

    @Mock
    ClusterDao clusterDaoMock;

    @Mock
    GeneExpressionDao geneExpressionDaoMock;

    TSnePlotCollator subject;

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

    private ImmutableMultimap<Integer, String> randomClusterGenerator(int numClusters, int numPointsPerCluster) {
        ImmutableMultimap.Builder<Integer, String> randomClustersBuilder = ImmutableMultimap.builder();
        for (int i = 0 ; i < numClusters ; i++) {
            for (int j = 0 ; j < numPointsPerCluster ; j++) {
                randomClustersBuilder.put(i, cellName(i * numPointsPerCluster + j));
            }
        }

        return randomClustersBuilder.build();
    }

    private ImmutableMap<String, Double> randomGeneExpression(int numPoints) {
        return ImmutableMap.copyOf(IntStream.range(0, numPoints).boxed().collect(Collectors.toMap(
                this::cellName,
                i -> ThreadLocalRandom.current().nextDouble()
        )));
    }

    @Before
    public void setUp() throws Exception {
        ImmutableMap<String, TSnePoint> tSnePlot = randomTSnePlotGenerator(NUM_CLUSTERS * NUM_POINTS_PER_CLUSTER);
        ImmutableMultimap<Integer, String> clusters = randomClusterGenerator(NUM_CLUSTERS, NUM_POINTS_PER_CLUSTER);
        ImmutableMap<String, Double> geneExpression = randomGeneExpression(NUM_CLUSTERS * NUM_POINTS_PER_CLUSTER);

        when(tSnePlotDaoMock.fetchTSnePlotPoints(EXPERIMENT_ACCESSION)).thenReturn(tSnePlot);
        when(clusterDaoMock.fetchClusters(EXPERIMENT_ACCESSION, NUM_CLUSTERS)).thenReturn(clusters);
        when(geneExpressionDaoMock.fetchGeneExpression(EXPERIMENT_ACCESSION, GENE_ID)).thenReturn(geneExpression);

        subject = new TSnePlotCollator(tSnePlotDaoMock, clusterDaoMock, geneExpressionDaoMock);
    }

    @Test
    public void getTSnePlotWithClusters() throws Exception {
        ImmutableMap<String, TSnePoint> tSnePlot = tSnePlotDaoMock.fetchTSnePlotPoints(EXPERIMENT_ACCESSION);
        ImmutableMultimap<Integer, String> clusters = clusterDaoMock.fetchClusters(EXPERIMENT_ACCESSION, NUM_CLUSTERS);

        TreeMultimap<Integer, TSnePoint> results = subject.getTSnePlotWithClusters(EXPERIMENT_ACCESSION, NUM_CLUSTERS);
        results.entries().forEach(entry -> {
            assertThat(clusters.get(entry.getKey()), hasItem(entry.getValue().name()));
            assertThat(tSnePlot.get(entry.getValue().name()), is(entry.getValue()));
        });
    }

    @Test
    public void getTSnePlotWithExpression() throws Exception {
        ImmutableMap<String, TSnePoint> tSnePlot = tSnePlotDaoMock.fetchTSnePlotPoints(EXPERIMENT_ACCESSION);
        ImmutableMap<String, Double> geneExpression = geneExpressionDaoMock.fetchGeneExpression(EXPERIMENT_ACCESSION, GENE_ID);

        ImmutableList<TSnePoint> results = subject.getTSnePlotWithExpression(EXPERIMENT_ACCESSION, GENE_ID);

        results.forEach(point -> {
            assertThat(TSnePoint.create(point.x(), point.y(), point.name()), is(tSnePlot.get(point.name())));
            assertThat(point.z(), is(geneExpression.get(point.name())));
        });
    }

    // TODO There will be cases in which gene expression cells is a superset of cells shown in the t-SNE plot but not
    // TODO the other way around, add tests for that
}