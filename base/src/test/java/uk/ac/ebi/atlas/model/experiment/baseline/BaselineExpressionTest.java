package uk.ac.ebi.atlas.model.experiment.baseline;

import com.google.gson.JsonObject;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

public class BaselineExpressionTest {
    @Test
    public void singleValuesDontHaveQuartiles() {
        assertThat(BaselineExpression.create("1.0").getQuartiles())
                .isEmpty();
    }

    @Test
    public void createFromString() {
        assertThat(BaselineExpression.create("1.0"))
                .isEqualTo(new BaselineExpression(1.0));
        assertThat(BaselineExpression.create("1.0, 2.0, 3.0, 4.0, 5.0"))
                .isEqualTo(new BaselineExpression(1.0, 2.0, 3.0, 4.0, 5.0));

    }

    @Test
    public void noDataCodesAreZero() {
        assertThat(BaselineExpression.create("-"))
                .isEqualTo(BaselineExpression.create("NT"))
                .isEqualTo(BaselineExpression.create("NA"))
                .isEqualTo(new BaselineExpression(0));
    }

    // This seems dangerous and not a good idea...
    @Test
    public void nullStringReturnsNull() {
        assertThat(BaselineExpression.create(null))
                .isNull();
    }

    @Test
    public void jsonSerializationWithoutQuartiles() {
        JsonObject jsonObject = new BaselineExpression(1.0).toJson();
        assertThat(jsonObject.get("value").getAsDouble()).isEqualTo(1.0);
        assertThat(jsonObject.get("quartiles")).isNull();
    }

    @Test
    public void jsonSerializationWithQuartiles() {
        JsonObject jsonObject = new BaselineExpression(1.0, 2.0, 3.0, 4.0, 5.0).toJson();
        assertThat(jsonObject.get("value").getAsDouble()).isEqualTo(3.0);
        assertThat(jsonObject.get("quartiles").getAsJsonObject().get("min").getAsDouble())
                .isEqualTo(1.0);
        assertThat(jsonObject.get("quartiles").getAsJsonObject().get("lower").getAsDouble())
                .isEqualTo(2.0);
        assertThat(jsonObject.get("quartiles").getAsJsonObject().get("median").getAsDouble())
                .isEqualTo(3.0);
        assertThat(jsonObject.get("quartiles").getAsJsonObject().get("upper").getAsDouble())
                .isEqualTo(4.0);
        assertThat(jsonObject.get("quartiles").getAsJsonObject().get("max").getAsDouble())
                .isEqualTo(5.0);
    }

    @Test
    public void badData() {
        assertThatIllegalArgumentException().isThrownBy(() -> BaselineExpression.create("1.0, 2.0, 3.0, 4.0"));
    }
}
