package uk.ac.ebi.atlas.experimentloader.analytics;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;
import uk.ac.ebi.atlas.dao.BaselineExpressionDao;
import uk.ac.ebi.atlas.dao.dto.BaselineExpressionDtoInputStream;
import uk.ac.ebi.atlas.dao.dto.BaselineExpressionDtoInputStreamFactory;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.transcript.TranscriptProfileDao;
import uk.ac.ebi.atlas.transcript.TranscriptProfilesLoader;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;

@Named
public class BaselineAnalyticsLoader implements AnalyticsLoader {

    private static final Logger LOGGER = Logger.getLogger(BaselineAnalyticsLoader.class);

    private final TranscriptProfilesLoader transcriptProfileLoader;
    private final BaselineExpressionDao baselineExpressionDao;
    private final BaselineExpressionDtoInputStreamFactory baselineExpressionDtoInputStreamFactory;
    private final TranscriptProfileDao transcriptProfileDao;

    @Inject
    public BaselineAnalyticsLoader(TranscriptProfilesLoader transcriptProfileLoader, BaselineExpressionDao baselineExpressionDao,
                                   BaselineExpressionDtoInputStreamFactory baselineExpressionDtoInputStreamFactory,
                                   TranscriptProfileDao transcriptProfileDao) {
        this.transcriptProfileLoader = transcriptProfileLoader;
        this.baselineExpressionDao = baselineExpressionDao;
        this.baselineExpressionDtoInputStreamFactory = baselineExpressionDtoInputStreamFactory;
        this.transcriptProfileDao = transcriptProfileDao;
    }

    @Override
    @Transactional
    public void loadAnalytics(String accession) throws IOException {
        transcriptProfileLoader.load(accession);
        loadBaselineExpressions(accession);
    }

    private void loadBaselineExpressions(String accession) {
        BaselineExpressionDtoInputStream baselineExpressionDtoInputStream =
                baselineExpressionDtoInputStreamFactory.createBaselineExpressionDtoInputStream(accession);
        baselineExpressionDao.loadBaselineExpressions(accession, baselineExpressionDtoInputStream);
    }

    @Override
    @Transactional
    public void deleteAnalytics(String accession) {
        baselineExpressionDao.deleteBaselineExpressions(accession);
        transcriptProfileDao.deleteTranscriptProfilesForExperiment(accession);
    }

}
