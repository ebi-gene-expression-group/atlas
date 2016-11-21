package uk.ac.ebi.atlas.experimentimport.analytics.baseline;

import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import org.springframework.transaction.annotation.Transactional;
import uk.ac.ebi.atlas.experimentimport.analytics.AnalyticsLoader;
import uk.ac.ebi.atlas.model.ExperimentType;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;

@Named
public class RnaSeqBaselineAnalyticsLoader implements AnalyticsLoader {

    private BaselineAnalyticsDAO analyticsDAO;
    private BaselineAnalyticsInputStreamFactory analyticsInputStreamFactory;

    @Inject
    public void setAnalyticsDAO(BaselineAnalyticsDAO analyticsDAO) {
        this.analyticsDAO = analyticsDAO;
    }

    @Inject
    public void setAnalyticsInputStreamFactory(BaselineAnalyticsInputStreamFactory analyticsInputStreamFactory) {
        this.analyticsInputStreamFactory = analyticsInputStreamFactory;
    }

    @Override
    public void loadAnalytics(String accession) throws IOException {
        ObjectInputStream<BaselineAnalytics> baselineAnalyticsInputStream =
                analyticsInputStreamFactory.create(accession, ExperimentType.RNASEQ_MRNA_BASELINE);
        analyticsDAO.loadAnalytics(accession, baselineAnalyticsInputStream);
    }

    @Override
    public void deleteAnalytics(String accession) {
        analyticsDAO.deleteAnalytics(accession);
    }

}
