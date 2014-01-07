package uk.ac.ebi.atlas.experimentloader.analytics;

import java.io.IOException;

public interface AnalyticsLoader {

    void loadAnalytics(String accession) throws IOException;

    void deleteAnalytics(String accession);

}
