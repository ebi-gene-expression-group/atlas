package uk.ac.ebi.atlas.model.experiment.baseline;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;
import com.google.gson.JsonObject;
import uk.ac.ebi.atlas.experimentimport.analytics.baseline.BaselineAnalytics;
import uk.ac.ebi.atlas.model.Expression;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class BaselineExpression implements Expression {
    private double level;
    private boolean known = true;
    private String dataColumnDescriptorId;
    private double[] quartiles;
    private static final NumberFormat FOUR_DP = new DecimalFormat("0.####");

    public BaselineExpression(double level) {
        this.level = level;
    }

    public BaselineExpression(double level, String dataColumnDescriptorId) {
        this(level, dataColumnDescriptorId, new double[]{level});
    }

    public BaselineExpression(double min,
                              double lowerQuartile,
                              double median,
                              double upperQuartile,
                              double max,
                              String dataColumnDescriptorId) {
        this(median, dataColumnDescriptorId, new double[]{min,lowerQuartile, median, upperQuartile, max});
    }


    private BaselineExpression(double level, String dataColumnDescriptorId, double[] quartiles) {
        this(level);
        this.dataColumnDescriptorId = dataColumnDescriptorId;
        this.quartiles = quartiles;
    }

    /*
    NT is deprecated, shouldn't be present
    "-" was documented as a "zero code"
    */
    private static final ImmutableList<String> zeros = ImmutableList.of("NA", "-", "NT");
    public static BaselineExpression create(String expressionLevelString, String assayGroupId){
        if (expressionLevelString == null) {
            return null;
        }

        if (zeros.contains(expressionLevelString)) {
            return new BaselineExpression(0.0, assayGroupId);
        }

        if (expressionLevelString.contains(",")) {
            String[] vals = expressionLevelString.split(",", 5);


            double min = Double.parseDouble(vals[0]);
            double max = Double.parseDouble(vals[4]);

            return min == max
                    ? new BaselineExpression(min, assayGroupId)
                    : new BaselineExpression(
                    min,
                    Double.parseDouble(vals[1]),
                    Double.parseDouble(vals[2]),
                    Double.parseDouble(vals[3]),
                    max,
                    assayGroupId);
        }

        return new BaselineExpression(Double.parseDouble(expressionLevelString), assayGroupId);
    }

    @Override
    public String getDataColumnDescriptorId(){
        return dataColumnDescriptorId;
    }

    public double[] getQuartiles() {
        return quartiles;
    }

    @Override
    public boolean isKnown() {
        return known;
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

        return Objects.equal(level, other.level) &&
                Objects.equal(dataColumnDescriptorId, other.dataColumnDescriptorId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(level, dataColumnDescriptorId);
    }

    static String removeTrailingZero(double value) {
        return FOUR_DP.format(value);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("level", level)
                .add("id", dataColumnDescriptorId)
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
