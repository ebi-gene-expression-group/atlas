package uk.ac.ebi.atlas.markergenes;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringUtils.leftPad;

public class RandomMarkerGeneInputStream implements ObjectInputStream<Object[]> {

    // Change to higher or lower values to decrease or increase, respectively, the probability of a gene being in the
    // same experiment with different cluster IDs
    private final int NUMBER_OF_GENES = 10;
    private final int K_VALUES = 4;

    private final List<Object[]> markerGenes;
    private int position;

    public RandomMarkerGeneInputStream(String experimentAccession, int size) {
        markerGenes = IntStream.rangeClosed(1, size).boxed()
                .map(i -> Triple.of(randomHumanGeneId(), experimentAccession, randomKCluster()))
                .distinct()
                .map(triple -> new Object[] {triple.getLeft(), triple.getMiddle(), triple.getRight().getLeft(), triple.getRight().getRight(), ThreadLocalRandom.current().nextDouble()})
                .collect(toList());
        position = 0;
    }

    @Override
    public Object[] readNext() {
        return position < markerGenes.size() ? markerGenes.get(position++) : null;
    }

    @Override
    public void close() throws IOException {

    }

    private String randomHumanGeneId() {
        return "ENSG" + leftPad(Integer.toString(ThreadLocalRandom.current().nextInt(1, NUMBER_OF_GENES)), 11, '0');
    }

    private Pair<Integer, Integer> randomKCluster() {
        int k = ThreadLocalRandom.current().nextInt(2, K_VALUES);
        return Pair.of(k, ThreadLocalRandom.current().nextInt(0, k));
    }
}
