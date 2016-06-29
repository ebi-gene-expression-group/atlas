package uk.ac.ebi.atlas.experimentpage.baseline;

import org.apache.commons.lang3.StringUtils;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.web.BaselineRequestPreferences;
import uk.ac.ebi.atlas.web.FilterFactorsConverter;

import java.util.Set;

public class PreferencesForBaselineExperiments {
    
    public PreferencesForBaselineExperiments() {
    }

    public void setPreferenceDefaults(BaselineRequestPreferences preferences, BaselineExperiment baselineExperiment) {

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

    private boolean allFactorsInSliceSelected(BaselineRequestPreferences preferences, BaselineExperiment experiment) {
        Set<Factor> selectedFilterFactors = FilterFactorsConverter.deserialize(preferences.getSerializedFilterFactors());

        Set<Factor> allFactorsInSlice;
        if(experiment.getExperimentalFactors().getAllFactorsOrderedByXML() != null &&
                !experiment.getExperimentalFactors().getAllFactorsOrderedByXML().isEmpty()) {
            allFactorsInSlice = experiment.getExperimentalFactors().getComplementFactorsByXML(selectedFilterFactors);
        } else {
            allFactorsInSlice = experiment.getExperimentalFactors().getComplementFactors(selectedFilterFactors);
        }

        return (preferences.getQueryFactorValues().size() == allFactorsInSlice.size());
    }
}
