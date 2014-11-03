package uk.ac.ebi.atlas.experimentimport.analytics.baseline;

import org.springframework.transaction.annotation.Transactional;
import uk.ac.ebi.atlas.experimentimport.analytics.AnalyticsLoader;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;

@Named
public class BaselineProteomicsAnalyticsLoader implements AnalyticsLoader {

    private final BaselineAnalyticsDao baselineAnalyticsDao;
    private final BaselineProteomicsAnalyticsInputStreamFactory baselineProteomicsAnalyticsInputStreamFactory;

    @Inject
    public BaselineProteomicsAnalyticsLoader(BaselineAnalyticsDao baselineAnalyticsDao,
                                             BaselineProteomicsAnalyticsInputStreamFactory baselineProteomicsAnalyticsInputStreamFactory) {
        this.baselineAnalyticsDao = baselineAnalyticsDao;
        this.baselineProteomicsAnalyticsInputStreamFactory = baselineProteomicsAnalyticsInputStreamFactory;
    }

    @Override
    @Transactional
    public void loadAnalytics(String accession) throws IOException {
        loadBaselineExpressions(accession);
    }

    private void loadBaselineExpressions(String accession) {
        BaselineProteomicsAnalyticsInputStream baselineProteomicsAnalyticsInputStream =
                baselineProteomicsAnalyticsInputStreamFactory.create(accession);
        baselineAnalyticsDao.loadAnalytics(accession, baselineProteomicsAnalyticsInputStream);
    }

    @Override
    @Transactional
    public void deleteAnalytics(String accession) {
        baselineAnalyticsDao.deleteAnalytics(accession);
    }

}
