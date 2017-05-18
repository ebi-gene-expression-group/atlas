package uk.ac.ebi.atlas.web;

import uk.ac.ebi.atlas.model.ExpressionUnit;

public class RnaSeqBaselineRequestPreferences extends BaselineRequestPreferences<ExpressionUnit.Absolute.Rna> {

    private static final double DEFAULT_CUTOFF = 0.5d;

    private ExpressionUnit.Absolute.Rna unit;

    @Override
    public double getDefaultCutoff() {
        return DEFAULT_CUTOFF;
    }

    public static RnaSeqBaselineRequestPreferences requestAllData(){
        RnaSeqBaselineRequestPreferences preferences = new RnaSeqBaselineRequestPreferences();
        BaselineRequestPreferences.setRequestAllData(preferences);
        return preferences;
    }

    public ExpressionUnit.Absolute.Rna getDefaultUnit(){
        return ExpressionUnit.Absolute.Rna.TPM;
    }

    @Override
    public ExpressionUnit.Absolute.Rna getUnit() {
        return unit;
    }

    public void setUnit(ExpressionUnit.Absolute.Rna unit) {
        this.unit = unit;
    }
}
