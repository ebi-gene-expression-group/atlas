package uk.ac.ebi.atlas.model.experiment.baseline;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;
import com.google.common.primitives.Doubles;
import com.google.gson.JsonObject;
import uk.ac.ebi.atlas.model.Expression;

import static com.google.common.base.Preconditions.checkArgument;

public class BaselineExpression implements Expression {
    private static final ImmutableList<String> NO_DATA_CODES = ImmutableList.of("NA", "-", "NT");

    private final double level;
    private final ImmutableList<Double> quartiles;

    public BaselineExpression(double level) {
        this(level, new double[0]);
    }

    public BaselineExpression(double min,
                              double lowerQuartile,
                              double median,
                              double upperQuartile,
                              double max) {
        this(median, new double[] {min, lowerQuartile, median, upperQuartile, max});
    }


    private BaselineExpression(double level, double[] quartiles) {
        this.level = level;
        this.quartiles = ImmutableList.copyOf(Doubles.asList(quartiles));
    }

    // NT is deprecated, shouldn't be present. "-" was documented as a "zero code. "NA" used in diff experiments.
    public static BaselineExpression create(String expressionLevelString) {
        if (expressionLevelString == null) {
            return null;
        }

        if (NO_DATA_CODES.contains(expressionLevelString)) {
            return new BaselineExpression(0.0);
        }

        if (expressionLevelString.contains(",")) {
            String[] vals = expressionLevelString.split(",", 5);
            checkArgument(vals.length == 5, String.format("Need 5 elements but got %s", expressionLevelString));

            return new BaselineExpression(Double.parseDouble(vals[0]),
                                          Double.parseDouble(vals[1]),
                                          Double.parseDouble(vals[2]),
                                          Double.parseDouble(vals[3]),
                                          Double.parseDouble(vals[4]));
        }

        return new BaselineExpression(Double.parseDouble(expressionLevelString));
    }

    public double[] getQuartiles() {
        return quartiles.stream().mapToDouble(Double::doubleValue).toArray();
    }

    public double getLevel() {
        return level;
    }

    public boolean isGreaterThanOrEqual(double level) {
        return Double.compare(this.level, level) >= 0;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        BaselineExpression other = (BaselineExpression) object;

        return Objects.equal(level, other.level);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(level);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("level", level)
                .toString();
    }

    @Override
    public JsonObject toJson() {
        JsonObject result = new JsonObject();

        result.addProperty("value", level);

        if (quartiles != null && quartiles.size() == 5) {
            JsonObject quartilesJson = new JsonObject();
            quartilesJson.addProperty("min", quartiles.get(0));
            quartilesJson.addProperty("lower", quartiles.get(1));
            quartilesJson.addProperty("median", quartiles.get(2));
            quartilesJson.addProperty("upper", quartiles.get(3));
            quartilesJson.addProperty("max", quartiles.get(4));
            result.add("quartiles", quartilesJson);
        }

        return result;
    }
}
