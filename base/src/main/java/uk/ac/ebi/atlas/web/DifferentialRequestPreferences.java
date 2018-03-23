package uk.ac.ebi.atlas.web;

import uk.ac.ebi.atlas.model.ExpressionUnit;
import uk.ac.ebi.atlas.model.experiment.differential.Regulation;

import javax.validation.constraints.Min;

public class DifferentialRequestPreferences extends ExperimentPageRequestPreferences<ExpressionUnit.Relative> {
    private static final double DEFAULT_CUTOFF = 0.05d;
    private static final double DEFAULT_FOLD_CHANGE_CUTOFF = 1d;

    private Regulation regulation = Regulation.UP_DOWN;

    @Min(value = 0, message = "log_2 fold change cutoff is an absolute amount, and so must be greater than zero")
    private double foldChangeCutoff = DEFAULT_FOLD_CHANGE_CUTOFF;

    @Override
    public double getDefaultCutoff() {
        return DEFAULT_CUTOFF;
    }

    public Regulation getRegulation() {
        return regulation;
    }

    public void setRegulation(Regulation regulation) {
        this.regulation = regulation;
    }

    public Double getFoldChangeCutoff() {
        return foldChangeCutoff;
    }

    public void setFoldChangeCutoff(Double foldChangeCutoff) {
        // handle no value case, eg: when textbox is left empty
        if (foldChangeCutoff != null) {
            this.foldChangeCutoff = foldChangeCutoff;
        }
    }

    @Override
    public ExpressionUnit.Relative getUnit() {
        return ExpressionUnit.Relative.FOLD_CHANGE;
    }
}
