package uk.ac.ebi.atlas.experimentimport.analytics.baseline;

import org.springframework.transaction.annotation.Transactional;
import uk.ac.ebi.atlas.experimentimport.analytics.AnalyticsLoader;
import uk.ac.ebi.atlas.experimentimport.analytics.baseline.transcript.RnaSeqBaselineTranscriptImporter;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;

@Named
public class BaselineProteomicsAnalyticsLoader implements AnalyticsLoader {

    private final BaselineAnalyticsDao baselineAnalyticsDao;
    private final BaselineAnalyticsInputStreamFactory baselineAnalyticsInputStreamFactory;
    private final RnaSeqBaselineTranscriptImporter rnaSeqBaselineTranscriptImporter;

    @Inject
    public BaselineProteomicsAnalyticsLoader(BaselineAnalyticsDao baselineAnalyticsDao,
                                             BaselineAnalyticsInputStreamFactory baselineAnalyticsInputStreamFactory,
                                             RnaSeqBaselineTranscriptImporter rnaSeqBaselineTranscriptImporter) {
        this.baselineAnalyticsDao = baselineAnalyticsDao;
        this.baselineAnalyticsInputStreamFactory = baselineAnalyticsInputStreamFactory;
        this.rnaSeqBaselineTranscriptImporter = rnaSeqBaselineTranscriptImporter;
    }

    @Override
    @Transactional
    public void loadAnalytics(String accession) throws IOException {
        //TODO: load into database
        //rnaSeqBaselineTranscriptImporter.importTranscripts(accession);
        //loadBaselineExpressions(accession);
    }

    private void loadBaselineExpressions(String accession) {
        BaselineAnalyticsInputStream baselineAnalyticsInputStream =
                baselineAnalyticsInputStreamFactory.create(accession);
        baselineAnalyticsDao.loadAnalytics(accession, baselineAnalyticsInputStream);
    }

    @Override
    @Transactional
    public void deleteAnalytics(String accession) {
        baselineAnalyticsDao.deleteAnalytics(accession);
        rnaSeqBaselineTranscriptImporter.deleteTranscripts(accession);
    }

}
