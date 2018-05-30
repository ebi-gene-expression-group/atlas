package uk.ac.ebi.atlas.web;

import uk.ac.ebi.atlas.model.ExpressionUnit;
import uk.ac.ebi.atlas.search.SemanticQuery;

public class RnaSeqBaselineRequestPreferences extends BaselineRequestPreferences<ExpressionUnit.Absolute.Rna> {
    private static final double DEFAULT_CUTOFF = 0.5d;
    private ExpressionUnit.Absolute.Rna unit = ExpressionUnit.Absolute.Rna.TPM;

    @Override
    public double getDefaultCutoff() {
        return DEFAULT_CUTOFF;
    }

    public static RnaSeqBaselineRequestPreferences requestAllData(ExpressionUnit.Absolute.Rna unit) {
        RnaSeqBaselineRequestPreferences preferences = new RnaSeqBaselineRequestPreferences();
        preferences.setCutoff(VERY_SMALL_NON_ZERO_VALUE);
        preferences.setGeneQuery(SemanticQuery.create());
        preferences.setUnit(unit);
        return preferences;
    }

    public void setUnit(ExpressionUnit.Absolute.Rna unit) {
        this.unit = unit;
    }

    @Override
    public ExpressionUnit.Absolute.Rna getUnit() {
        return unit;
    }
}
