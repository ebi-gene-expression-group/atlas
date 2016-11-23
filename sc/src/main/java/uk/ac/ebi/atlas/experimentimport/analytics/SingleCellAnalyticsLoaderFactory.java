package uk.ac.ebi.atlas.experimentimport.analytics;

import uk.ac.ebi.atlas.model.ExperimentType;

import javax.inject.Named;
import java.io.IOException;

@Named
public class SingleCellAnalyticsLoaderFactory implements AnalyticsLoaderFactory {
    @Override
    public AnalyticsLoader getLoader(ExperimentType experimentType) {
        return new AnalyticsLoader() {
            @Override
            public void loadAnalytics(String accession) throws IOException {
                //
            }

            @Override
            public void deleteAnalytics(String accession) {
                //
            }
        };
    }
}
