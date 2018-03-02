package uk.ac.ebi.atlas.experimentimport.analytics;

import java.io.IOException;

public interface AnalyticsLoader {
    default void loadAnalytics(String accession) throws IOException {}
    default void deleteAnalytics(String accession) {}
}
