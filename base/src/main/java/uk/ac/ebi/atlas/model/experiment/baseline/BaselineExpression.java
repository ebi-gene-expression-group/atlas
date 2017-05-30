package uk.ac.ebi.atlas.model.experiment.baseline;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.gson.JsonObject;
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
        this(level, dataColumnDescriptorId, new double[]{});
    }

    public BaselineExpression(double[] quartiles, String dataColumnDescriptorId) {
        this(quartiles[2], dataColumnDescriptorId, quartiles);
    }

    // I got made public for the serializer, do not use otherwise! TODO improve
    public BaselineExpression(double level, String dataColumnDescriptorId, double[] quartiles) {
        this(level);
        this.dataColumnDescriptorId = dataColumnDescriptorId;
        this.quartiles = quartiles;
    }

    public BaselineExpression(String expressionLevelString, String dataColumnDescriptorId) {

        switch (expressionLevelString) {
            case "NT": // For factors not studied in the multiexperiment heatmap
                level = 0.0;
                known = false;
                break;
            case "NA": // Treat as if zero
                level = 0;
                known = true;
                break;
            default:
                level = Double.parseDouble(expressionLevelString);
                known = true;
                break;
        }
        this.dataColumnDescriptorId = dataColumnDescriptorId;
        this.quartiles = new double[]{};
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
