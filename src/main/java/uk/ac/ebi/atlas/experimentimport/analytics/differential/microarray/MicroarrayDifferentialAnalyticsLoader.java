package uk.ac.ebi.atlas.experimentimport.analytics.differential.microarray;

import org.springframework.transaction.annotation.Transactional;
import uk.ac.ebi.atlas.experimentimport.analytics.AnalyticsLoader;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExperimentConfiguration;
import uk.ac.ebi.atlas.trader.ConfigurationTrader;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;

public class MicroarrayDifferentialAnalyticsLoader implements AnalyticsLoader {

    private final MicroarrayDifferentialAnalyticsDao analyticsDao;
    private final MicroarrayDifferentialAnalyticsInputStreamFactory analyticsInputStreamFactory;
    private final ConfigurationTrader configurationTrader;

    public MicroarrayDifferentialAnalyticsLoader(MicroarrayDifferentialAnalyticsDao analyticsDao, MicroarrayDifferentialAnalyticsInputStreamFactory analyticsInputStreamFactory, ConfigurationTrader configurationTrader) {
        this.analyticsDao = analyticsDao;
        this.analyticsInputStreamFactory = analyticsInputStreamFactory;
        this.configurationTrader = configurationTrader;
    }

    @Override
    @Transactional
    public void loadAnalytics(String accession) throws IOException {
        MicroarrayExperimentConfiguration configuration = configurationTrader.getMicroarrayExperimentConfiguration(accession);

        for (String arrayDesign : configuration.getArrayDesignAccessions()) {
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
