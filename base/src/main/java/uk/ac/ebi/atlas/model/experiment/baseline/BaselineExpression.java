package uk.ac.ebi.atlas.model.experiment.baseline;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;
import com.google.gson.JsonObject;
import uk.ac.ebi.atlas.model.Expression;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class BaselineExpression implements Expression {
    private double level;
    private double[] quartiles;
    private static final NumberFormat FOUR_DP = new DecimalFormat("0.####");

    public BaselineExpression(double level) {
        this(level, new double[]{level});
    }

    public BaselineExpression(double min,
                              double lowerQuartile,
                              double median,
                              double upperQuartile,
                              double max) {
        this(median, new double[]{min,lowerQuartile, median, upperQuartile, max});
    }


    private BaselineExpression(double level, double[] quartiles) {
        this.level = level;
        this.quartiles = quartiles;
    }

    /*
    NT is deprecated, shouldn't be present
    "-" was documented as a "zero code"
    */
    private static final ImmutableList<String> zeros = ImmutableList.of("NA", "-", "NT");
    public static BaselineExpression create(String expressionLevelString){
        if (expressionLevelString == null) {
            return null;
        }

        if (zeros.contains(expressionLevelString)) {
            return new BaselineExpression(0.0);
        }

        if (expressionLevelString.contains(",")) {
            String[] vals = expressionLevelString.split(",", 5);


            double min = Double.parseDouble(vals[0]);
            double max = Double.parseDouble(vals[4]);

            return min == max
                    ? new BaselineExpression(min)
                    : new BaselineExpression(
                    min,
                    Double.parseDouble(vals[1]),
                    Double.parseDouble(vals[2]),
                    Double.parseDouble(vals[3]),
                    max
            );
        }

        return new BaselineExpression(Double.parseDouble(expressionLevelString));
    }

    public double[] getQuartiles() {
        return quartiles;
    }

    public double getLevel() {
        return level;
    }

    public boolean isGreaterThanOrEqual(double level) {
        return Double.compare(getLevel(), level) >= 0;
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

    static String removeTrailingZero(double value) {
        return FOUR_DP.format(value);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("level", level)
                .toString();
    }

    /*
    Used to be: BaselineExpressionViewModel which also provides properties the old heatmap requires
    see atlas_heatmap:src/load/Data.js for how the properties are read in
     */
    @Override
    public JsonObject toJson() {
        JsonObject result = new JsonObject();

        result.addProperty("value", level);

        if(quartiles!=null && quartiles.length == 5){
            result.add("quartiles",Quartiles.create(quartiles).toJson());
        }
        return result;
    }
}
