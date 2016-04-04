package uk.ac.ebi.atlas.experimentpage.baseline;

import org.apache.commons.lang3.StringUtils;
import uk.ac.ebi.atlas.experimentpage.context.BaselineRequestContext;
import uk.ac.ebi.atlas.experimentpage.context.BaselineRequestContextBuilder;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.web.BaselineRequestPreferences;
import uk.ac.ebi.atlas.web.FilterFactorsConverter;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Set;

@Named
public class PreferencesForBaselineExperiments {

    private BaselineRequestContextBuilder baselineRequestContextBuilder;
    private FilterFactorsConverter filterFactorsConverter;

    @Inject
    public PreferencesForBaselineExperiments(BaselineRequestContextBuilder baselineRequestContextBuilder,
                                           FilterFactorsConverter filterFactorsConverter) {
        this.baselineRequestContextBuilder = baselineRequestContextBuilder;
        this.filterFactorsConverter = filterFactorsConverter;
    }

    public void setPreferenceDefaults(BaselineRequestPreferences preferences, BaselineExperiment baselineExperiment) {

        if (StringUtils.isBlank(preferences.getQueryFactorType())) {
            preferences.setQueryFactorType(baselineExperiment.getExperimentalFactors().getDefaultQueryFactorType());
        }

        if (StringUtils.isBlank(preferences.getSerializedFilterFactors())) {
            preferences.setSerializedFilterFactors(filterFactorsConverter.serialize(baselineExperiment.getExperimentalFactors().getDefaultFilterFactors()));
        }

        if (allFactorsInSliceSelected(preferences, baselineExperiment)) {
            preferences.setSpecific(false);
        }

    }

    private boolean allFactorsInSliceSelected(BaselineRequestPreferences preferences, BaselineExperiment experiment) {
        Set<Factor> selectedFilterFactors = filterFactorsConverter.deserialize(preferences.getSerializedFilterFactors());

        Set<Factor> allFactorsInSlice;
        if(experiment.getExperimentalFactors().getAllFactorsOrderedByXML() != null &&
                !experiment.getExperimentalFactors().getAllFactorsOrderedByXML().isEmpty()) {
            allFactorsInSlice = experiment.getExperimentalFactors().getComplementFactorsByXML(selectedFilterFactors);
        } else {
            allFactorsInSlice = experiment.getExperimentalFactors().getComplementFactors(selectedFilterFactors);
        }

        return (preferences.getQueryFactorValues().size() == allFactorsInSlice.size());
    }

    public BaselineRequestContext buildRequestContext(BaselineExperiment experiment, BaselineRequestPreferences preferences) {

        return baselineRequestContextBuilder.forExperiment(experiment)
                .withPreferences(preferences)
                .build();
    }
}
