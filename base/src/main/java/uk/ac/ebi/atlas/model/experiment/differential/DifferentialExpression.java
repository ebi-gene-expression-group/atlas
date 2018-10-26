package uk.ac.ebi.atlas.model.experiment.differential;

import com.google.common.base.Objects;
import com.google.gson.JsonObject;
import uk.ac.ebi.atlas.model.Expression;

import java.text.MessageFormat;

public class DifferentialExpression implements Expression {
    private static final double SMALLEST_P_VALUE_ALLOWED = 1E-125;

    private double pValue;
    private double foldChange;

    // If adjusted p-value is smaller than the minimum allowed value, treat it as 0D. This condition is checked when
    // reading from the TSV file
    public DifferentialExpression(double pValue, double foldChange) {
        this.pValue = (pValue < SMALLEST_P_VALUE_ALLOWED) ? 0D : pValue;
        this.foldChange = foldChange;
    }

    public double getPValue() {
        return pValue;
    }

    public double getFoldChange() {
        return foldChange;
    }

    public boolean isRegulatedLike(Regulation regulation) {
        return Regulation.UP_DOWN.equals(regulation)
                || isLikeUpRegulation(regulation)
                || isLikeDownRegulation(regulation);
    }

    private boolean isLikeUpRegulation(Regulation regulation) {
        return Regulation.UP.equals(regulation) && isOverExpressed();
    }

    private boolean isLikeDownRegulation(Regulation regulation) {
        return Regulation.DOWN.equals(regulation) && isUnderExpressed();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if ((null == object) || (getClass() != object.getClass())) {
            return false;
        }

        DifferentialExpression other = (DifferentialExpression) object;

        return Objects.equal(foldChange, other.foldChange) &&
                Objects.equal(pValue, other.pValue);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(pValue, foldChange);
    }

    @Override
    public double getLevel() {
        return getFoldChange();
    }

    public boolean isOverExpressed() {
        return foldChange > 0;
    }

    public boolean isUnderExpressed() {
        return foldChange < 0;
    }

    public double getAbsoluteFoldChange() {
        return Math.abs(foldChange);
    }

    /*
    see atlas_heatmap:src/load/Data.js for how the properties are read in
    */
    @Override
    public JsonObject toJson() {
        JsonObject result = new JsonObject();
        result.addProperty("foldChange", foldChange);
        result.addProperty("pValue", pValue);
        return result;
    }

    @Override
    public String toString() {
        return MessageFormat.format("DifferentialExpression foldChange={0} pValue={1}", foldChange, pValue);
    }
}
