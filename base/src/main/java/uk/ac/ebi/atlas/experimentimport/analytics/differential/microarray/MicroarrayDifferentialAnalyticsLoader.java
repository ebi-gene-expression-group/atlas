package uk.ac.ebi.atlas.experimentimport.analytics.differential.microarray;

import uk.ac.ebi.atlas.experimentimport.analytics.AnalyticsLoader;
import uk.ac.ebi.atlas.trader.ConfigurationTrader;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.Set;

@Named
public class MicroarrayDifferentialAnalyticsLoader implements AnalyticsLoader {

    private MicroarrayDifferentialAnalyticsDao analyticsDao;
    private MicroarrayDifferentialAnalyticsInputStreamFactory analyticsInputStreamFactory;
    private ConfigurationTrader configurationTrader;

    @Inject
    public void setAnalyticsDao(MicroarrayDifferentialAnalyticsDao analyticsDao) {
        this.analyticsDao = analyticsDao;
    }

    @Inject
    public void setAnalyticsInputStreamFactory(MicroarrayDifferentialAnalyticsInputStreamFactory analyticsInputStreamFactory) {
        this.analyticsInputStreamFactory = analyticsInputStreamFactory;
    }

    @Inject
    public void setConfigurationTrader(ConfigurationTrader configurationTrader) {
        this.configurationTrader = configurationTrader;
    }

    @Override
    public void loadAnalytics(String accession) throws IOException {
        Set<String> arrayDesigns =
                configurationTrader.getMicroarrayExperimentConfiguration(accession).getArrayDesignAccessions();

        for (String arrayDesign : arrayDesigns) {
            MicroarrayDifferentialAnalyticsInputStream analyticsInputStream =
                    analyticsInputStreamFactory.create(accession, arrayDesign);
            analyticsDao.loadAnalytics(accession, arrayDesign, analyticsInputStream);
        }
    }

    @Override
    public void deleteAnalytics(String accession) {
        analyticsDao.deleteAnalytics(accession);
    }

}
