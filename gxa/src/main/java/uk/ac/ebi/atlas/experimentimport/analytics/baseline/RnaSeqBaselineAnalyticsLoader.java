package uk.ac.ebi.atlas.experimentimport.analytics.baseline;

import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import org.springframework.transaction.annotation.Transactional;
import uk.ac.ebi.atlas.experimentimport.analytics.AnalyticsLoader;
import uk.ac.ebi.atlas.model.ExperimentType;

import java.io.IOException;

public class RnaSeqBaselineAnalyticsLoader implements AnalyticsLoader {

    private final BaselineAnalyticsDAO baselineAnalyticsDAO;
    private final BaselineAnalyticsInputStreamFactory baselineAnalyticsInputStreamFactory;

    public RnaSeqBaselineAnalyticsLoader(BaselineAnalyticsDAO baselineAnalyticsDAO, BaselineAnalyticsInputStreamFactory baselineAnalyticsInputStreamFactory) {
        this.baselineAnalyticsDAO = baselineAnalyticsDAO;
        this.baselineAnalyticsInputStreamFactory = baselineAnalyticsInputStreamFactory;
    }

    @Override
    @Transactional
    public void loadAnalytics(String accession) throws IOException {
        loadBaselineExpressions(accession);
    }

    private void loadBaselineExpressions(String accession) {
        ObjectInputStream<BaselineAnalytics> baselineAnalyticsInputStream = baselineAnalyticsInputStreamFactory.create
                (accession, ExperimentType.RNASEQ_MRNA_BASELINE);
        baselineAnalyticsDAO.loadAnalytics(accession, baselineAnalyticsInputStream);
    }

    @Override
    @Transactional
    public void deleteAnalytics(String accession) {
        baselineAnalyticsDAO.deleteAnalytics(accession);
    }

}
