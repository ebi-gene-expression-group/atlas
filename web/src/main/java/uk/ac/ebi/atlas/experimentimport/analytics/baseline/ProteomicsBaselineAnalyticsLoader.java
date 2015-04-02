package uk.ac.ebi.atlas.experimentimport.analytics.baseline;

import org.springframework.transaction.annotation.Transactional;
import uk.ac.ebi.atlas.experimentimport.analytics.AnalyticsLoader;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;

@Named
public class ProteomicsBaselineAnalyticsLoader implements AnalyticsLoader {

    private final BaselineAnalyticsDao baselineAnalyticsDao;
    private final ProteomicsBaselineAnalyticsInputStreamFactory proteomicsBaselineAnalyticsInputStreamFactory;

    @Inject
    public ProteomicsBaselineAnalyticsLoader(BaselineAnalyticsDao baselineAnalyticsDao,
                                             ProteomicsBaselineAnalyticsInputStreamFactory proteomicsBaselineAnalyticsInputStreamFactory) {
        this.baselineAnalyticsDao = baselineAnalyticsDao;
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
        baselineAnalyticsDao.loadAnalytics(accession, proteomicsBaselineAnalyticsInputStream);
    }

    @Override
    @Transactional
    public void deleteAnalytics(String accession) {
        baselineAnalyticsDao.deleteAnalytics(accession);
    }

}
