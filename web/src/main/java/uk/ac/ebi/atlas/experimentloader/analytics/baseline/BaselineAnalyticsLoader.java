package uk.ac.ebi.atlas.experimentloader.analytics.baseline;

import org.springframework.transaction.annotation.Transactional;
import uk.ac.ebi.atlas.experimentloader.analytics.AnalyticsLoader;
import uk.ac.ebi.atlas.transcript.TranscriptProfileDao;
import uk.ac.ebi.atlas.transcript.TranscriptProfilesLoader;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;

@Named
public class BaselineAnalyticsLoader implements AnalyticsLoader {

    private final TranscriptProfilesLoader transcriptProfileLoader;
    private final BaselineAnalyticsDao baselineAnalyticsDao;
    private final BaselineAnalyticsInputStreamFactory baselineAnalyticsInputStreamFactory;
    private final TranscriptProfileDao transcriptProfileDao;

    @Inject
    public BaselineAnalyticsLoader(TranscriptProfilesLoader transcriptProfileLoader, BaselineAnalyticsDao baselineAnalyticsDao,
                                   BaselineAnalyticsInputStreamFactory baselineAnalyticsInputStreamFactory,
                                   TranscriptProfileDao transcriptProfileDao) {
        this.transcriptProfileLoader = transcriptProfileLoader;
        this.baselineAnalyticsDao = baselineAnalyticsDao;
        this.baselineAnalyticsInputStreamFactory = baselineAnalyticsInputStreamFactory;
        this.transcriptProfileDao = transcriptProfileDao;
    }

    @Override
    @Transactional
    public void loadAnalytics(String accession) throws IOException {
        transcriptProfileLoader.load(accession);
        loadBaselineExpressions(accession);
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
        transcriptProfileDao.deleteTranscriptProfilesForExperiment(accession);
    }

}
