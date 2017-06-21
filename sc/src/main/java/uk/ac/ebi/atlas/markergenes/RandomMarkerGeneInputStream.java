package uk.ac.ebi.atlas.markergenes;

import org.apache.commons.lang3.tuple.Pair;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.apache.commons.lang3.StringUtils.leftPad;

public class RandomMarkerGeneInputStream implements ObjectInputStream<MarkerGene> {

    private final List<MarkerGene> markerGenes;
    private int position;

    public RandomMarkerGeneInputStream(int size) {
        markerGenes = IntStream.rangeClosed(1, size).boxed()
                .map(i -> MarkerGene.create(randomHumanGeneId(), randomExperimentOver9000(), randomPerplexityCluster(), ThreadLocalRandom.current().nextDouble()))
                .collect(Collectors.toList());
        position = 0;
    }

    @Override
    public MarkerGene readNext() {
        return position < markerGenes.size() ? markerGenes.get(position++) : null;
    }

    @Override
    public void close() throws IOException {

    }

    private String randomHumanGeneId() {
        return "ENSG" + leftPad(Integer.toString(ThreadLocalRandom.current().nextInt(1, 999999999)), 11, '0');
    }

    private String randomExperimentOver9000() {
        return "E-MTAB-" + Integer.toString(ThreadLocalRandom.current().nextInt(9000, 12000));
    }

    private Pair<Integer, Integer> randomPerplexityCluster() {
        int k = ThreadLocalRandom.current().nextInt(2, 11);
        return Pair.of(k, ThreadLocalRandom.current().nextInt(0, k));
    }
}
