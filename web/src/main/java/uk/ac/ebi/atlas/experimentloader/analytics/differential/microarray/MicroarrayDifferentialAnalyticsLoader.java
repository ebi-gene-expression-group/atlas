package uk.ac.ebi.atlas.experimentloader.analytics.differential.microarray;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;
import uk.ac.ebi.atlas.experimentloader.analytics.AnalyticsLoader;

import javax.inject.Named;
import java.io.IOException;

@Named
public class MicroarrayDifferentialAnalyticsLoader implements AnalyticsLoader {

    private static final Logger LOGGER = Logger.getLogger(MicroarrayDifferentialAnalyticsLoader.class);

    @Override
    @Transactional
    public void loadAnalytics(String accession) throws IOException {
    }

    @Override
    @Transactional
    public void deleteAnalytics(String accession) {
    }

}
