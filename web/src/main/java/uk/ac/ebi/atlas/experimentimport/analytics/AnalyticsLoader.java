package uk.ac.ebi.atlas.experimentimport.analytics;

import java.io.IOException;

public interface AnalyticsLoader {

    void loadAnalytics(String accession) throws IOException;

    void deleteAnalytics(String accession);

}
