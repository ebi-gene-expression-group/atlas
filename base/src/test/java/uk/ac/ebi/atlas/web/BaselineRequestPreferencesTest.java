package uk.ac.ebi.atlas.web;

public class BaselineRequestPreferencesTest {

    public static BaselineRequestPreferences get(){
        return new BaselineRequestPreferences() {
            @Override
            public double getDefaultCutoff() {
                return 0.0;
            }
        };
    }

}