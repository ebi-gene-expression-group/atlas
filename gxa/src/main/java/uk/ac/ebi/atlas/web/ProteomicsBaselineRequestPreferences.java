package uk.ac.ebi.atlas.web;


import uk.ac.ebi.atlas.model.ExpressionUnit;

public class ProteomicsBaselineRequestPreferences extends BaselineRequestPreferences<ExpressionUnit.Absolute.Protein>{
    public static final double DEFAULT_CUTOFF = 0.0d;

    @Override
    public double getDefaultCutoff() {
        return DEFAULT_CUTOFF;
    }

    public static ProteomicsBaselineRequestPreferences requestAllData(){
        ProteomicsBaselineRequestPreferences preferences = new ProteomicsBaselineRequestPreferences();
        BaselineRequestPreferences.setRequestAllData(preferences);
        return preferences;
    }

    @Override
    public ExpressionUnit.Absolute.Protein getUnit() {
        return ExpressionUnit.Absolute.Protein.ANY;
    }
}
