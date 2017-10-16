package uk.ac.ebi.atlas.tsne;

import com.atlassian.util.concurrent.LazyReference;
import com.google.common.collect.TreeMultimap;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

// TODO This method is for testing the limits of Highcharts
public class RandomTSnePlot {
    private static final int RANDOM_EXPERIMENT_DATA_POINTS_COUNT = 1000000;
    private final LazyReference<List<TSnePoint>> randomPoints = new LazyReference<List<TSnePoint>>() {
        @Override
        protected List<TSnePoint> create() throws Exception {
            return IntStream.range(0, RANDOM_EXPERIMENT_DATA_POINTS_COUNT)
                    .boxed()
                    .map(i -> TSnePoint.create(
                            ThreadLocalRandom.current().nextDouble(-20.0, 20.0),
                            ThreadLocalRandom.current().nextDouble(-10.0, 10.0),
                            "Cell " + Integer.toString(i)
                    ))
                    .collect(Collectors.toList());
        }
    };

    private final HashMap<Integer, TreeMultimap<Integer, TSnePoint>> clusterEntriesCache = new HashMap<>();

    public TreeMultimap<Integer, TSnePoint> getTSnePlotWithClusters(int k) {
        if (clusterEntriesCache.get(k) == null) {
            TreeSet<Integer> cutPoints = new TreeSet<>();
            while (cutPoints.size() < k - 1) {
                cutPoints.add(ThreadLocalRandom.current().nextInt(1, RANDOM_EXPERIMENT_DATA_POINTS_COUNT));
            }

            TreeMultimap<Integer, TSnePoint> points = TreeMultimap.create(Comparator.<Integer> naturalOrder(), TSnePoint.getNameComparator());
            int previousCutPoint = 0;
            int clusterId = 0;
            for (int cutPoint : cutPoints) {
                for (TSnePoint point : randomPoints.get().subList(previousCutPoint, cutPoint)) {
                    points.put(clusterId, point);
                }
                clusterId++;
                previousCutPoint = cutPoint;
            }
            for (TSnePoint point : randomPoints.get().subList(previousCutPoint, randomPoints.get().size())) {
                points.put(clusterId, point);
            }

            clusterEntriesCache.put(k, points);
        }

        return clusterEntriesCache.get(k);
    }
}
