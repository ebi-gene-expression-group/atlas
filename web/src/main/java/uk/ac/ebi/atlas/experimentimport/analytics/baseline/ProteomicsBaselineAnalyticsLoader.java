package uk.ac.ebi.atlas.experimentimport.analytics.baseline;

import org.springframework.transaction.annotation.Transactional;
import uk.ac.ebi.atlas.experimentimport.analytics.AnalyticsLoader;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;

@Named
public class ProteomicsBaselineAnalyticsLoader implements AnalyticsLoader {

    private final BaselineAnalyticsDAO baselineAnalyticsDAO;
    private final ProteomicsBaselineAnalyticsInputStreamFactory proteomicsBaselineAnalyticsInputStreamFactory;

    @Inject
    public ProteomicsBaselineAnalyticsLoader(BaselineAnalyticsDAO baselineAnalyticsDAO,
                                             ProteomicsBaselineAnalyticsInputStreamFactory proteomicsBaselineAnalyticsInputStreamFactory) {
        this.baselineAnalyticsDAO = baselineAnalyticsDAO;
        this.proteomicsBaselineAnalyticsInputStreamFactory = proteomicsBaselineAnalyticsInputStreamFactory;
    }

    @Override
    @Transactional
    public void loadAnalytics(String accession) throws IOException {
        loadBaselineExpressions(accession);
    }

    private void loadBaselineExpressions(String accession) {
        ProteomicsBaselineAnalyticsInputStream proteomicsBaselineAnalyticsInputStream =
                proteomicsBaselineAnalyticsInputStreamFactory.create(accession);
        baselineAnalyticsDAO.loadAnalytics(accession, proteomicsBaselineAnalyticsInputStream);
    }

    @Override
    @Transactional
    public void deleteAnalytics(String accession) {
        baselineAnalyticsDAO.deleteAnalytics(accession);
    }

}
