package uk.ac.ebi.atlas.testutils;

import com.google.common.collect.ImmutableList;
import uk.ac.ebi.atlas.solr.BioentityPropertyName;
import uk.ac.ebi.atlas.experimentpage.tsne.TSnePoint;
import uk.ac.ebi.atlas.species.Species;
import uk.ac.ebi.atlas.species.SpeciesProperties;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Collections.emptyList;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;
import static org.apache.commons.lang3.StringUtils.capitalize;
import static uk.ac.ebi.atlas.solr.BioentityPropertyName.UNKNOWN;

public class RandomDataTestUtils {
    private static final ThreadLocalRandom RNG = ThreadLocalRandom.current();

    protected RandomDataTestUtils() {
        throw new UnsupportedOperationException();
    }

    private static final int ENSEMBLE_GENE_ID_NUM_LENGTH = 12;
    private static final int ENA_SEQ_RUN_NUM_LENGTH = 7;

    public static String generateRandomExperimentAccession() {
        // n / 456, 975, 543, 024 chance of clashing for n experiments in the test database, let’s roll!
        return "E-" + randomAlphabetic(4).toUpperCase() + "-" + randomNumeric(1, 6);
    }

    public static String generateRandomExperimentAccession(String accessionPattern) {
        return "E-" + accessionPattern.toUpperCase() + "-" + randomNumeric(1, 6);
    }

    public static String generateRandomEnsemblGeneId() {
        return "ENS" + randomAlphabetic(4).toUpperCase() + randomNumeric(ENSEMBLE_GENE_ID_NUM_LENGTH);
    }

    public static String generateRandomEfoAccession() {
        // https://www.ebi.ac.uk/ols/ontologies/efo
        // Version: 2.99
        // Number of terms: 22023
        return "EFO_" +  String.format("%07d", RNG.nextInt(1, 22023));
    }

    public static List<String[]> generateRandomClusters(int fromK, int toK, int numberOfCells) {
        ImmutableList.Builder<String[]> clustersTsvBuilder = ImmutableList.builder();

        clustersTsvBuilder.add(
                Stream.concat(
                        Stream.of("sel.K", "K"),
                        generateRandomSingleCellRnaSeqRunIds(numberOfCells).stream())
                .toArray(String[]::new));

        // It’s a bit convoluted, but randomClustersLine will be invoked with true only once, the first iteration in
        // which thisClusterSelK becomes true
        boolean selKSet = false;
        for (int k = fromK; k <= toK; k++) {
            boolean thisClusterSelK = RNG.nextBoolean();
            clustersTsvBuilder.add(randomClustersLine(!selKSet && thisClusterSelK, k, numberOfCells));
            selKSet = selKSet || thisClusterSelK;
        }

        return clustersTsvBuilder.build();
    }

    private static Set<String> generateRandomSingleCellRnaSeqRunIds(int n) {
        Set<String> runIds = new HashSet<>(n);
        while (runIds.size() < n) {
            runIds.add(generateRandomRnaSeqRunId());
        }
        return runIds;
    }

    public static String generateRandomRnaSeqRunId() {
        return "SRR" + randomNumeric(1, ENA_SEQ_RUN_NUM_LENGTH);
    }

    private static String[] randomClustersLine(boolean selK, int k, int n) {
        List<String> clusterIds = new ArrayList<>(n);
        clusterIds.add(Boolean.toString(selK).toUpperCase());
        clusterIds.add(Integer.toString(k));
        while (clusterIds.size() < n + 2) {
            clusterIds.add(Integer.toString(RNG.nextInt(1, k + 1)));
        }
        return clusterIds.toArray(new String[0]);
    }

    public static Set<TSnePoint.Dto> generateRandomTSnePointDtos(int n) {
        Set<String> runIds = generateRandomSingleCellRnaSeqRunIds(n);

        return runIds
                .stream()
                .map(id -> TSnePoint.Dto.create(RNG.nextDouble(), RNG.nextDouble(), id))
                .collect(Collectors.toSet());
    }

    public static Set<TSnePoint.Dto> generateRandomTSnePointDtosWithExpression(int n) {
        Set<TSnePoint.Dto> tSnePointDtos = new HashSet<>(n);
        while (tSnePointDtos.size() < n) {
            tSnePointDtos.add(
                    TSnePoint.Dto.create(
                            RNG.nextDouble(), RNG.nextDouble(), RNG.nextDouble(), generateRandomRnaSeqRunId()));
        }

        return tSnePointDtos;
    }

    public static Set<TSnePoint.Dto> generateRandomTSnePointDtosWithClusters(int n, int k) {
        Set<TSnePoint.Dto> tSnePointDtos = new HashSet<>(n);
        while (tSnePointDtos.size() < n) {
            tSnePointDtos.add(
                    TSnePoint.Dto.create(
                            RNG.nextDouble(),
                            RNG.nextDouble(),
                            RNG.nextInt(1, k + 1),
                            generateRandomRnaSeqRunId()));
        }

        return tSnePointDtos;
    }

    public static BioentityPropertyName generateRandomKnownBioentityPropertyName() {
        BioentityPropertyName propertyName = UNKNOWN;
        while (propertyName == UNKNOWN) {
            propertyName =
                    BioentityPropertyName.values()[
                            RNG.nextInt(0, BioentityPropertyName.values().length)];
        }

        return propertyName;
    }

    public static Species generateRandomSpecies() {
        String first = capitalize(randomAlphabetic(1, 10).toLowerCase());
        if (first.length() == 1) {
            first = first + ".";
        }

        String second = randomAlphabetic(6, 10);

        return new Species(
                first + " " + second,
                SpeciesProperties.create(
                        first + "_" + second,
                        "DEVELOPMENTAL_STAGE",
                        "Dorne",
                        ImmutableList.of()));
    }
}
