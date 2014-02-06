package uk.ac.ebi.atlas.experimentimport.analytics.baseline.transcript;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.trader.loader.BaselineExperimentExpressionLevelFile;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;

@Named
@Scope("singleton")
public class RnaSeqBaselineTranscriptImporter {

    private static final Logger LOGGER = Logger.getLogger(RnaSeqBaselineTranscriptImporter.class);

    private final RnaSeqBaselineTranscriptDao rnaSeqBaselineTranscriptDao;
    private final RnaSeqBaselineTranscriptReaderFactory rnaSeqBaselineTranscriptReaderFactory;
    private final BaselineExperimentExpressionLevelFile baselineExperimentExpressionLevelFile;

    @Inject
    public RnaSeqBaselineTranscriptImporter(RnaSeqBaselineTranscriptDao rnaSeqBaselineTranscriptDao,
                                            RnaSeqBaselineTranscriptReaderFactory rnaSeqBaselineTranscriptReaderFactory,
                                            BaselineExperimentExpressionLevelFile baselineExperimentExpressionLevelFile) {
        this.rnaSeqBaselineTranscriptDao = rnaSeqBaselineTranscriptDao;
        this.rnaSeqBaselineTranscriptReaderFactory = rnaSeqBaselineTranscriptReaderFactory;
        this.baselineExperimentExpressionLevelFile = baselineExperimentExpressionLevelFile;
    }

    public void importTranscripts(String accession) {
        String[] orderedAssayGroupIds = baselineExperimentExpressionLevelFile.readOrderedAssayGroupIds(accession);
        try (RnaSeqBaselineTranscriptReader rnaSeqBaselineTranscriptReader = rnaSeqBaselineTranscriptReaderFactory.create(accession, orderedAssayGroupIds)) {
            rnaSeqBaselineTranscriptDao.insert(accession, rnaSeqBaselineTranscriptReader);
        } catch (IOException e) {
            LOGGER.error(String.format("Cannot close RnaSeqBaselineTranscriptReader: %s", e.getMessage()));
            LOGGER.error(e.getStackTrace());
        }
    }

    public void deleteTranscripts(String accession) {
        rnaSeqBaselineTranscriptDao.delete(accession);
    }
}
