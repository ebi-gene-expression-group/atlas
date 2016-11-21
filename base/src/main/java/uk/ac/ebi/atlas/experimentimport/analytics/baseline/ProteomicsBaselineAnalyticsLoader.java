package uk.ac.ebi.atlas.experimentimport.analytics.baseline;

import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.experimentimport.analytics.AnalyticsLoader;
import org.springframework.transaction.annotation.Transactional;
import uk.ac.ebi.atlas.model.ExperimentType;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;

@Named
public class ProteomicsBaselineAnalyticsLoader implements AnalyticsLoader {

    private BaselineAnalyticsDAO analyticsDao;
    private BaselineAnalyticsInputStreamFactory analyticsInputStreamFactory;

    @Inject
    public void setAnalyticsDAO(BaselineAnalyticsDAO analyticsDao) {
        this.analyticsDao = analyticsDao;
    }

    @Inject
    public void setAnalyticsInputStreamFactory(BaselineAnalyticsInputStreamFactory analyticsInputStreamFactory) {
        this.analyticsInputStreamFactory = analyticsInputStreamFactory;
    }

    @Override
    public void loadAnalytics(String accession) throws IOException {
        ObjectInputStream<BaselineAnalytics> analyticsInputStream =
                analyticsInputStreamFactory.create(accession, ExperimentType.PROTEOMICS_BASELINE);
        analyticsDao.loadAnalytics(accession, analyticsInputStream);
    }

    @Override
    public void deleteAnalytics(String accession) {
        analyticsDao.deleteAnalytics(accession);
    }

}
