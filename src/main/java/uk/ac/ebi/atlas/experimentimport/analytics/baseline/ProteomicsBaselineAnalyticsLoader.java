package uk.ac.ebi.atlas.experimentimport.analytics.baseline;

import org.springframework.transaction.annotation.Transactional;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.experimentimport.analytics.AnalyticsLoader;
import uk.ac.ebi.atlas.model.ExperimentType;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;

public class ProteomicsBaselineAnalyticsLoader implements AnalyticsLoader {

    private final BaselineAnalyticsDAO baselineAnalyticsDAO;
    private final BaselineAnalyticsInputStreamFactory proteomicsBaselineAnalyticsInputStreamFactory;

    public ProteomicsBaselineAnalyticsLoader(BaselineAnalyticsDAO baselineAnalyticsDAO,
                                            BaselineAnalyticsInputStreamFactory proteomicsBaselineAnalyticsInputStreamFactory) {
        this.baselineAnalyticsDAO = baselineAnalyticsDAO;
        this.proteomicsBaselineAnalyticsInputStreamFactory = proteomicsBaselineAnalyticsInputStreamFactory;
    }

    @Override
    @Transactional
    public void loadAnalytics(String accession) throws IOException {
        loadBaselineExpressions(accession);
    }

    private void loadBaselineExpressions(String accession) {
        ObjectInputStream<BaselineAnalytics> proteomicsBaselineAnalyticsInputStream =
                proteomicsBaselineAnalyticsInputStreamFactory.create(accession, ExperimentType.PROTEOMICS_BASELINE);
        baselineAnalyticsDAO.loadAnalytics(accession, proteomicsBaselineAnalyticsInputStream);
    }

    @Override
    @Transactional
    public void deleteAnalytics(String accession) {
        baselineAnalyticsDAO.deleteAnalytics(accession);
    }

}
