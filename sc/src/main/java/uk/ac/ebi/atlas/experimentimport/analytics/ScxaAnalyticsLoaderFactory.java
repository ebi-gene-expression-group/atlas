package uk.ac.ebi.atlas.experimentimport.analytics;

import uk.ac.ebi.atlas.model.experiment.ExperimentType;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class ScxaAnalyticsLoaderFactory implements AnalyticsLoaderFactory {

    private final AnalyticsLoader singleCellAnalyticsLoader;

    @Inject
    public ScxaAnalyticsLoaderFactory(AnalyticsLoader singleCellAnalyticsLoader) {
        this.singleCellAnalyticsLoader = singleCellAnalyticsLoader;
    }

    @Override
    public AnalyticsLoader getLoader(ExperimentType experimentType) {
        return singleCellAnalyticsLoader;
    }
}
