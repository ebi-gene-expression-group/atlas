package uk.ac.ebi.atlas.model.experiment.differential.microarray;

import com.google.common.base.MoreObjects;
import uk.ac.ebi.atlas.model.experiment.differential.Contrast;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExpression;

public class MicroarrayExpression extends DifferentialExpression {

    private double tstatistic;

    public MicroarrayExpression(double pValue, double foldChange, double tstatistic, Contrast contrast) {
        super(pValue, foldChange, contrast);
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
                .toString();
    }
}
