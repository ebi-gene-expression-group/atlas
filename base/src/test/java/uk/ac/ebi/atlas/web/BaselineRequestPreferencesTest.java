package uk.ac.ebi.atlas.web;

import uk.ac.ebi.atlas.model.ExpressionUnit;

public class BaselineRequestPreferencesTest {
    protected BaselineRequestPreferencesTest() {
        throw new UnsupportedOperationException();
    }

    public static BaselineRequestPreferences<ExpressionUnit.Absolute.Rna> get() {
        return new BaselineRequestPreferences<ExpressionUnit.Absolute.Rna>() {
            @Override
            public double getDefaultCutoff() {
                return 0.0;
            }

            public ExpressionUnit.Absolute.Rna getUnit() {
                return ExpressionUnit.Absolute.Rna.TPM;
            }
        };
    }
}
