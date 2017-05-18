package uk.ac.ebi.atlas.web;

public class RnaSeqBaselineRequestPreferences extends BaselineRequestPreferences {

    private static final double DEFAULT_CUTOFF = 0.5d;

    @Override
    public double getDefaultCutoff() {
        return DEFAULT_CUTOFF;
    }

    public static RnaSeqBaselineRequestPreferences requestAllData(){
        RnaSeqBaselineRequestPreferences preferences = new RnaSeqBaselineRequestPreferences();
        BaselineRequestPreferences.setRequestAllData(preferences);
        return preferences;
    }
}
