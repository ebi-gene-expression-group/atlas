package uk.ac.ebi.atlas.tsne;

import org.junit.Test;

import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;


public class TSnePointTest {
    private TSnePoint randomValuedPoint(String name) {
        return TSnePoint.create(
                ThreadLocalRandom.current().nextDouble(),
                ThreadLocalRandom.current().nextDouble(),
                Optional.of(ThreadLocalRandom.current().nextDouble()),
                name);
    }

    @Test
    public void create() throws Exception {
        assertThat(TSnePoint.create(0.0, 0.0, "A"), is(TSnePoint.create(0.0, 0.0, Optional.empty(), "A")));
    }

    @Test
    public void pointsCanBeComparedByName() throws Exception {
        TSnePoint pointNamedA = randomValuedPoint("A");
        TSnePoint pointNamedB = randomValuedPoint("B");
        TSnePoint pointNamedC = randomValuedPoint("C");
        TSnePoint pointNamedD = randomValuedPoint("D");

        assertThat(
                Arrays.stream(
                        new TSnePoint[] {pointNamedD, pointNamedB, pointNamedC, pointNamedA})
                        .sorted(TSnePoint.getNameComparator()).collect(Collectors.toList()),
                contains(pointNamedA, pointNamedB, pointNamedC, pointNamedD));
    }
}