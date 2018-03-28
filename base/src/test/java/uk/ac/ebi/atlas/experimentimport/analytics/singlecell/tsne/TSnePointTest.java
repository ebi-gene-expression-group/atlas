package uk.ac.ebi.atlas.experimentimport.analytics.singlecell.tsne;

import org.junit.Test;

import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.ac.ebi.atlas.utils.GsonProvider.GSON;

public class TSnePointTest {
    private TSnePoint randomValuedPoint(String name) {
        return TSnePoint.create(
                ThreadLocalRandom.current().nextDouble(),
                ThreadLocalRandom.current().nextDouble(),
                ThreadLocalRandom.current().nextDouble(),
                name);
    }

    @Test
    public void createWithoutExpression() {
        assertThat(TSnePoint.create(0.0, 0.0, "A").expressionLevel()).isEqualTo(Optional.empty());
    }

    @Test
    public void pointsAreComparedByName() {
        TSnePoint pointNamedA = randomValuedPoint("A");
        TSnePoint pointNamedB = randomValuedPoint("B");
        TSnePoint pointNamedC = randomValuedPoint("C");
        TSnePoint pointNamedD = randomValuedPoint("D");

        assertThat(
                Arrays.stream(new TSnePoint[] {pointNamedD, pointNamedB, pointNamedC, pointNamedA})
                        .sorted(TSnePoint.getNameComparator()).collect(Collectors.toList())).
                containsExactly(pointNamedA, pointNamedB, pointNamedC, pointNamedD);
    }

    @Test
    public void toJson() {
        TSnePoint autoValuedInstance = TSnePoint.create(0.0, 0.0, "");

        TSnePoint tSnePointWithoutExpression = TSnePoint.create(0.0, 0.0, "A");
        assertThat(GSON.fromJson(GSON.toJson(tSnePointWithoutExpression), autoValuedInstance.getClass()))
                .isEqualTo(tSnePointWithoutExpression);

        TSnePoint tSnePointWithExpression = TSnePoint.create(0.0, 0.0, 1.0, "A");
        assertThat(GSON.fromJson(GSON.toJson(tSnePointWithExpression), autoValuedInstance.getClass()))
                .isEqualTo(tSnePointWithExpression);

    }
}