package uk.ac.ebi.atlas.model.experiment.baseline;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoSerializable;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.ArrayUtils;
import uk.ac.ebi.atlas.model.Expression;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class BaselineExpression implements Expression, KryoSerializable {
    private double level;
    private String levelString;
    private String dataColumnDescriptorId;
    private double[] quartiles;
    private static final NumberFormat FOUR_DP = new DecimalFormat("0.####");

    // No-arg constructor required by Kryo. Can be private because Kryo uses reflection.
    private BaselineExpression() {}

    public BaselineExpression(double level) {
        this.level = level;
    }

    public BaselineExpression(double level, String dataColumnDescriptorId) {
        this(level, dataColumnDescriptorId, new double[]{});
    }

    public BaselineExpression(double[] quartiles, String dataColumnDescriptorId) {
        this(quartiles[2], dataColumnDescriptorId, quartiles);
    }

    private BaselineExpression(double level, String dataColumnDescriptorId, double[] quartiles) {
        this(level);
        this.dataColumnDescriptorId = dataColumnDescriptorId;
        this.levelString = removeTrailingZero(level);
        this.quartiles = quartiles;
    }

    public BaselineExpression(String expressionLevelString, String dataColumnDescriptorId) {
        this.levelString = expressionLevelString;

        switch (expressionLevelString) {
            case "NT":  //Non-Tissue
                level = 0;
                break;
            case "NA":
                // treat as if zero
                level = 0;
                break;
            default:
                level = Double.parseDouble(expressionLevelString);
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

    public double getLevel() {
        return level;
    }

    public String getLevelAsString() {
        return levelString;
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
                .add("levelString", levelString)
                .add("id", dataColumnDescriptorId)
                .toString();
    }


    @Override
    public void write(Kryo kryo, Output output) {
        output.writeDouble(level);
        output.writeString(levelString);
        output.writeString(dataColumnDescriptorId);
        boolean hasQuartiles = !ArrayUtils.isEmpty(quartiles);
        output.writeBoolean(hasQuartiles);
        output.writeDoubles(quartiles);
    }

    @Override
    public void read(Kryo kryo, Input input) {
        level = input.readDouble();
        levelString = input.readString();
        dataColumnDescriptorId = input.readString();
        boolean hasQuartiles = input.readBoolean();
        quartiles = hasQuartiles ? input.readDoubles(5) : input.readDoubles(0);
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
