package uk.ac.ebi.atlas.experimentpage.baseline;

import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.web.BaselineRequestPreferences;
import uk.ac.ebi.atlas.web.FilterFactorsConverter;
import org.apache.commons.lang3.StringUtils;

public class PreferencesForBaselineExperiments {

    private PreferencesForBaselineExperiments() {
    }

    public static void setPreferenceDefaults(BaselineRequestPreferences preferences, BaselineExperiment baselineExperiment) {

        if (StringUtils.isBlank(preferences.getQueryFactorType())) {
            preferences.setQueryFactorType(baselineExperiment.getExperimentalFactors().getDefaultQueryFactorType());
        }

        if (StringUtils.isBlank(preferences.getSerializedFilterFactors())) {
            preferences.setSerializedFilterFactors(FilterFactorsConverter.serialize(baselineExperiment.getExperimentalFactors().getDefaultFilterFactors()));
        }

        if (allFactorsInSliceSelected(preferences, baselineExperiment)) {
            preferences.setSpecific(false);
        }

    }

    private static boolean allFactorsInSliceSelected(BaselineRequestPreferences preferences, BaselineExperiment
            experiment) {
        return (preferences.getQueryFactorValues().size() == experiment.getExperimentalFactors().getComplementFactors(FilterFactorsConverter.deserialize(preferences.getSerializedFilterFactors())).size());
    }
}
