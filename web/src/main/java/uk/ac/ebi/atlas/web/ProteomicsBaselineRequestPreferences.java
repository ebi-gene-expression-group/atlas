package uk.ac.ebi.atlas.web;


public class ProteomicsBaselineRequestPreferences extends BaselineRequestPreferences {
    public static final double DEFAULT_CUTOFF = 0.0d;

    @Override
    public double getDefaultCutoff() {
        return DEFAULT_CUTOFF;
    }

}
