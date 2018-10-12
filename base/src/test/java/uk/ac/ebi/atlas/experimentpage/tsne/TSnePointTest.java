package uk.ac.ebi.atlas.experimentpage.tsne;

import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.ac.ebi.atlas.utils.GsonProvider.GSON;

public class TSnePointTest {
    @Test
    public void createWithoutExpression() {
        assertThat(TSnePoint.create(0.0, 0.0, "A").expressionLevel()).isEqualTo(Optional.empty());
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
