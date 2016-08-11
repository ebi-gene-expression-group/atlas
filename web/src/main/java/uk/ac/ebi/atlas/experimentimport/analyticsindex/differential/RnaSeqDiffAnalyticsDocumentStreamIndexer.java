package uk.ac.ebi.atlas.experimentimport.analyticsindex.differential;

import com.google.common.collect.SetMultimap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.experimentimport.analytics.differential.DifferentialAnalytics;
import uk.ac.ebi.atlas.experimentimport.analytics.differential.rnaseq.RnaSeqDifferentialAnalyticsInputStreamFactory;
import uk.ac.ebi.atlas.experimentimport.analyticsindex.AnalyticsIndexDAO;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.profiles.IterableObjectInputStream;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.Map;

@Named
public class RnaSeqDiffAnalyticsDocumentStreamIndexer {

    private static final Logger LOGGER = LoggerFactory.getLogger(RnaSeqDiffAnalyticsDocumentStreamIndexer.class);

    private final RnaSeqDifferentialAnalyticsInputStreamFactory rnaSeqDifferentialAnalyticsInputStreamFactory;
    private AnalyticsIndexDAO analyticsIndexDAO;

    @Inject
    public RnaSeqDiffAnalyticsDocumentStreamIndexer(RnaSeqDifferentialAnalyticsInputStreamFactory rnaSeqDifferentialAnalyticsInputStreamFactory, AnalyticsIndexDAO analyticsIndexDAO) {
        this.rnaSeqDifferentialAnalyticsInputStreamFactory = rnaSeqDifferentialAnalyticsInputStreamFactory;
        this.analyticsIndexDAO = analyticsIndexDAO;
    }

    public int index(DifferentialExperiment experiment, SetMultimap<String, String> conditionSearchTermsByContrastGroupId,
                     Map<String, Integer> numReplicatesByContrastId,
                     Map<String, String> bioentityIdToIdentifierSearch,
                     int batchSize) {



        try (ObjectInputStream<? extends DifferentialAnalytics> closeableInputStream =
                     rnaSeqDifferentialAnalyticsInputStreamFactory.create(experiment.getAccession())) {

            LOGGER.info("Start indexing " + experiment.getAccession());
            StopWatch stopWatch = new StopWatch(getClass().getSimpleName());
            stopWatch.start();

            int count = analyticsIndexDAO.addDocuments(new DiffAnalyticsDocumentStream(experiment,new
                    IterableObjectInputStream<>(closeableInputStream), conditionSearchTermsByContrastGroupId, numReplicatesByContrastId, bioentityIdToIdentifierSearch), batchSize);

            stopWatch.stop();
            LOGGER.info("Done indexing {}, indexed {} documents in {} seconds", experiment.getAccession(), count, stopWatch
                    .getTotalTimeSeconds());

            return count;

        } catch (IOException e) {
            throw new DiffAnalyticsDocumentStreamIndexerException(e);
        }

    }

    private class DiffAnalyticsDocumentStreamIndexerException extends RuntimeException {
        DiffAnalyticsDocumentStreamIndexerException(Exception e) {
            super(e);
        }
    }

}
