package uk.ac.ebi.atlas.experimentimport.analytics;

import uk.ac.ebi.atlas.model.ExperimentType;

public interface AnalyticsLoaderFactory {
    AnalyticsLoader getLoader(ExperimentType experimentType);
}
