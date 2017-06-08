package uk.ac.ebi.atlas.experimentimport.analytics;

import uk.ac.ebi.atlas.model.experiment.ExperimentType;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;

@Named
public class SingleCellAnalyticsLoaderFactory implements AnalyticsLoaderFactory {

    SingleCellBaselineAnalyticsLoader singleCellBaselineAnalyticsLoader;

    @Inject
    public SingleCellAnalyticsLoaderFactory(SingleCellBaselineAnalyticsLoader singleCellBaselineAnalyticsLoader){
        this.singleCellBaselineAnalyticsLoader = singleCellBaselineAnalyticsLoader;
    }

    @Override
    public AnalyticsLoader getLoader(ExperimentType experimentType) {
        return singleCellBaselineAnalyticsLoader;
    }
}
