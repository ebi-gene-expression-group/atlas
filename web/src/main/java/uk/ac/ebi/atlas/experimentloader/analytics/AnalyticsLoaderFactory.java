package uk.ac.ebi.atlas.experimentloader.analytics;

import uk.ac.ebi.atlas.model.ExperimentType;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class AnalyticsLoaderFactory {

    private AnalyticsLoader baselineAnalyticsLoader;

    @Inject
    public AnalyticsLoaderFactory(AnalyticsLoader baselineAnalyticsLoader) {
        this.baselineAnalyticsLoader = baselineAnalyticsLoader;
    }

    public AnalyticsLoader getLoader(ExperimentType experimentType) {
        if (experimentType.isBaseline()) {
            return baselineAnalyticsLoader;
        }
        return null;
    }
}
