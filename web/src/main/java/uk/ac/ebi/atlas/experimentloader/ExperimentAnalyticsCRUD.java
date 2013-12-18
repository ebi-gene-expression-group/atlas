package uk.ac.ebi.atlas.experimentloader;

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
public class ExperimentAnalyticsCRUD {

    private static final Logger LOGGER = Logger.getLogger(ExperimentAnalyticsCRUD.class);

    private TranscriptProfilesLoader transcriptProfileLoader;
    private BaselineExpressionDao baselineExpressionDao;
    private BaselineExpressionDtoInputStreamFactory baselineExpressionDtoInputStreamFactory;
    private TranscriptProfileDao transcriptProfileDao;

    // requires no-arg constructor for @Transactional proxying, hence setter injection
    // of dependencies
    public ExperimentAnalyticsCRUD() {
    }

    @Inject
    public void setTranscriptProfileDao(TranscriptProfileDao transcriptProfileDao) {
        this.transcriptProfileDao = transcriptProfileDao;
    }

    @Inject
    public void setTranscriptProfileLoader(TranscriptProfilesLoader transcriptProfileLoader) {
        this.transcriptProfileLoader = transcriptProfileLoader;
    }

    @Inject
    public void setBaselineExpressionDao(BaselineExpressionDao baselineExpressionDao) {
        this.baselineExpressionDao = baselineExpressionDao;
    }

    @Inject
    public void setBaselineExpressionDtoInputStreamFactory(BaselineExpressionDtoInputStreamFactory baselineExpressionDtoInputStreamFactory) {
        this.baselineExpressionDtoInputStreamFactory = baselineExpressionDtoInputStreamFactory;
    }

    @Transactional
    public void loadAnalytics(String accession, ExperimentType experimentType) throws IOException {
        LOGGER.info(String.format("loadAnalytics for %s type %s", accession, experimentType));
        if (experimentType.isBaseline()) {
            loadBaselineAnalytics(accession);
        } else {
            throw new UnsupportedOperationException("Unknown experiment type" + experimentType);
        }
    }

    public void loadBaselineAnalytics(String accession) throws IOException {
        transcriptProfileLoader.load(accession);
        loadBaselineExpressions(accession);
    }

    private void loadBaselineExpressions(String accession) {
        baselineExpressionDao.deleteBaselineExpressions(accession);
        BaselineExpressionDtoInputStream baselineExpressionDtoInputStream =
                baselineExpressionDtoInputStreamFactory.createBaselineExpressionDtoInputStream(accession);
        baselineExpressionDao.loadBaselineExpressions(accession, baselineExpressionDtoInputStream);
    }

    @Transactional
    public void deleteAnalytics(String accession, ExperimentType experimentType) {
        LOGGER.info(String.format("loadAnalytics for %s type %s", accession, experimentType));
        if (experimentType.isBaseline()) {
            deleteBaselineAnalytics(accession);
        } else {
            throw new UnsupportedOperationException("Unknown experiment type" + experimentType);
        }

    }

    private void deleteBaselineAnalytics(String accession) {
        baselineExpressionDao.deleteBaselineExpressions(accession);
        transcriptProfileDao.deleteTranscriptProfilesForExperiment(accession);
    }
}
