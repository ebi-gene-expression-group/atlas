package uk.ac.ebi.atlas.model.baseline;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoSerializable;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.google.common.base.Objects;
import org.apache.commons.lang.ArrayUtils;
import uk.ac.ebi.atlas.model.Expression;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Set;

public class BaselineExpression implements Expression, KryoSerializable {
    private double level;
    private String levelString;
    private FactorGroup factorGroup;
    private boolean known;
    private double[] quartiles;
    public static final NumberFormat FOUR_DP = new DecimalFormat("0.####");

    // No-arg constructor required by Kryo. Can be private because Kryo uses reflection.
    private BaselineExpression() {}

    public BaselineExpression(double level) {
        this.level = level;
        this.known = true;
    }

    public BaselineExpression(double level, FactorGroup factorGroup) {
        this(level, factorGroup, new double[]{});
    }

    public BaselineExpression(double[] quartiles, FactorGroup factorGroup) {
        this(quartiles[2], factorGroup, quartiles);
    }

    private BaselineExpression(double level, FactorGroup factorGroup, double[] quartiles) {
        this(level);
        this.factorGroup = factorGroup;
        this.levelString = removeTrailingZero(level);
        this.quartiles = quartiles;
    }

    public BaselineExpression(String expressionLevelString, FactorGroup factorGroup) {
        this.levelString = expressionLevelString;

        switch (expressionLevelString) {
            case "NT":  //Non-Tissue
                level = 0;
                known = false;
                break;
            case "NA":
                // treat as if zero
                level = 0;
                known = true;
                break;
            default:
                level = Double.parseDouble(expressionLevelString);
                known = true;
                break;
        }
        this.factorGroup = factorGroup;
        this.quartiles = new double[]{};
    }

    public double[] getQuartiles() {
        return quartiles;
    }

    public FactorGroup getFactorGroup() {
        return factorGroup;
    }

    public double getLevel() {
        if (!isKnown()) {
            throw new UnsupportedOperationException("BaselineExpression level is " + levelString + ". Call isKnown() first to check.");
        }
        return level;
    }

    public boolean isKnown() {
        return known;
    }

    public String getLevelAsString() {
        return levelString;
    }

    public boolean isGreaterThan(double level) {
        return Double.compare(getLevel(), level) > 0;
    }

    public void setFactorGroup(FactorGroup factorGroup) {
        this.factorGroup = factorGroup;
    }

    public Factor getFactor(String type) {
        for (Factor factor : factorGroup) {
            if (factor.getType().equals(type)) {
                return factor;
            }
        }
        throw new IllegalStateException("BaselineExpression doesn't contain factor for a given type");
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
                Objects.equal(factorGroup, other.factorGroup);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(level, factorGroup);
    }

    public boolean containsAll(Set<Factor> factors) {
        return factorGroup.containsAll(factors);
    }

    static String removeTrailingZero(double value) {
        return FOUR_DP.format(value);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("levelString", levelString)
                .add("factorGroup", factorGroup)
                .toString();
    }


    @Override
    public void write(Kryo kryo, Output output) {
        output.writeDouble(level);
        output.writeString(levelString);
        output.writeBoolean(known);
        boolean hasQuartiles = !ArrayUtils.isEmpty(quartiles);
        output.writeBoolean(hasQuartiles);
        output.writeDoubles(quartiles);
    }

    @Override
    public void read(Kryo kryo, Input input) {
        level = input.readDouble();
        levelString = input.readString();
        known = input.readBoolean();
        boolean hasQuartiles = input.readBoolean();
        quartiles = hasQuartiles ? input.readDoubles(5) : input.readDoubles(0);
    }
}