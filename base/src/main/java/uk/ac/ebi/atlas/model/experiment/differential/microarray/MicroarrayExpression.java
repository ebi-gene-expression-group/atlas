package uk.ac.ebi.atlas.model.experiment.differential.microarray;

import com.google.common.base.MoreObjects;
import com.google.gson.JsonObject;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExpression;

public class MicroarrayExpression extends DifferentialExpression {

    private double tstatistic;

    public MicroarrayExpression(double pValue, double foldChange, double tstatistic) {
        super(pValue, foldChange);
        this.tstatistic = tstatistic;
    }

    //It's used in jsp EL
    public double getTstatistic() {
        return tstatistic;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("p-value", getPValue())
                .add("foldChange", getFoldChange())
                .add("t stat", getTstatistic())
                .toString();
    }

    @Override
    public JsonObject toJson(){
        JsonObject result = super.toJson();
        result.addProperty("tStat", tstatistic);
        return result;
    }
}
