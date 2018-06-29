package uk.ac.ebi.atlas.testutils;

import com.google.common.collect.ImmutableList;
import uk.ac.ebi.atlas.experimentpage.tsne.TSnePoint;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;

public class RandomDataTestUtils {
    public static String getRandomExperimentAccession() {
        // n / 456,975,543,024 chance of clashing for n experiments in the test database, let’s roll!
        return "E-" + randomAlphabetic(4).toUpperCase() + "-" + randomNumeric(1, 6);
    }

    public static String getRandomEnsemblGeneId() {
        return "ENS" + randomAlphabetic(4).toUpperCase() + randomNumeric(12);
    }

    public static List<String[]> getRandomClusters(int fromK, int toK, int numberOfCells) {
        ImmutableList.Builder<String[]> clustersTsvBuilder = ImmutableList.builder();

        clustersTsvBuilder.add(
                Stream.concat(
                        Stream.of("sel.K", "K"),
                        randomSingleCellRnaSeqRunIds(numberOfCells).stream())
                .toArray(String[]::new));

        // It’s a bit convoluted, but randomClustersLine will be invoked with true only once, the first iteration in
        // which thisClusterSelK becomes true
        boolean selKSet = false;
        for (int k = fromK ; k <= toK ; k++) {
            boolean thisClusterSelK = ThreadLocalRandom.current().nextBoolean();

            clustersTsvBuilder.add(randomClustersLine(!selKSet && thisClusterSelK, k, numberOfCells));

            selKSet = selKSet || thisClusterSelK;
        }

        return clustersTsvBuilder.build();
    }

    public static Set<String> randomSingleCellRnaSeqRunIds(int n) {
        Set<String> runIds = new HashSet<>(n);
        while (runIds.size() < n) {
            runIds.add(randomRnaSeqRunId());
        }
        return runIds;
    }

    private static String randomRnaSeqRunId() {
        return "SRR" + randomNumeric(1, 7);
    }

    private static String[] randomClustersLine(boolean selK, int k, int n) {
        List<String> clusterIds = new ArrayList<>(n);
        clusterIds.add(Boolean.toString(selK).toUpperCase());
        clusterIds.add(Integer.toString(k));
        while (clusterIds.size() < n + 2) {
            clusterIds.add(Integer.toString(ThreadLocalRandom.current().nextInt(1, k + 1)));
        }
        return clusterIds.toArray(new String[0]);
    }

    public static Set<TSnePoint.Dto> randomTSnePointDtos(int n) {
        Random random = ThreadLocalRandom.current();

        Set<String> runIds = randomSingleCellRnaSeqRunIds(n);

        return runIds
                .stream()
                .map(id -> TSnePoint.Dto.create(random.nextDouble(), random.nextDouble(), id))
                .collect(Collectors.toSet());
    }

    public static Set<TSnePoint.Dto> randomTSnePointDtosWithExpression(int n) {
        Random random = ThreadLocalRandom.current();

        Set<TSnePoint.Dto> tSnePointDtos = new HashSet<>(n);
        while (tSnePointDtos.size() < n) {
            tSnePointDtos.add(
                    TSnePoint.Dto.create(
                            random.nextDouble(), random.nextDouble(), random.nextDouble(), randomRnaSeqRunId()));
        }

        return tSnePointDtos;
    }

    public static Set<TSnePoint.Dto> randomTSnePointDtosWithClusters(int n, int k) {
        Set<TSnePoint.Dto> tSnePointDtos = new HashSet<>(n);
        while (tSnePointDtos.size() < n) {
            tSnePointDtos.add(
                    TSnePoint.Dto.create(
                            ThreadLocalRandom.current().nextDouble(),
                            ThreadLocalRandom.current().nextDouble(),
                            ThreadLocalRandom.current().nextInt(1, k + 1),
                            randomRnaSeqRunId()));
        }

        return tSnePointDtos;
    }
}
