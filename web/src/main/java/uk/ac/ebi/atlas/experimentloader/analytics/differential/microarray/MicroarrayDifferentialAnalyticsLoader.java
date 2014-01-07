package uk.ac.ebi.atlas.experimentloader.analytics.differential.microarray;

import org.springframework.transaction.annotation.Transactional;
import uk.ac.ebi.atlas.experimentloader.analytics.AnalyticsLoader;
import uk.ac.ebi.atlas.model.ConfigurationTrader;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExperimentConfiguration;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;

@Named
public class MicroarrayDifferentialAnalyticsLoader implements AnalyticsLoader {

    private final MicroarrayDifferentialAnalyticsDao analyticsDao;
    private final MicroarrayDifferentialAnalyticsInputStreamFactory analyticsInputStreamFactory;
    private final ConfigurationTrader configurationTrader;

    @Inject
    public MicroarrayDifferentialAnalyticsLoader(MicroarrayDifferentialAnalyticsDao analyticsDao, MicroarrayDifferentialAnalyticsInputStreamFactory analyticsInputStreamFactory, ConfigurationTrader configurationTrader) {
        this.analyticsDao = analyticsDao;
        this.analyticsInputStreamFactory = analyticsInputStreamFactory;
        this.configurationTrader = configurationTrader;
    }

    @Override
    @Transactional
    public void loadAnalytics(String accession) throws IOException {
        MicroarrayExperimentConfiguration configuration = configurationTrader.getMicroarrayExperimentConfiguration(accession);

        for (String arrayDesign : configuration.getArrayDesignNames()) {
            MicroarrayDifferentialAnalyticsInputStream analyticsInputStream = analyticsInputStreamFactory.create(accession, arrayDesign);
            analyticsDao.loadAnalytics(accession, arrayDesign, analyticsInputStream);
        }

    }

    @Override
    @Transactional
    public void deleteAnalytics(String accession) {
        analyticsDao.deleteAnalytics(accession);
    }

}
