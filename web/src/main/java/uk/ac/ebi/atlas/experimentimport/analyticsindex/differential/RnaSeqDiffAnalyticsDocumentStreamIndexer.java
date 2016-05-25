package uk.ac.ebi.atlas.experimentimport.analyticsindex.differential;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.SetMultimap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.experimentimport.analytics.differential.DifferentialAnalytics;
import uk.ac.ebi.atlas.experimentimport.analytics.differential.rnaseq.RnaSeqDifferentialAnalyticsInputStreamFactory;
import uk.ac.ebi.atlas.experimentimport.analyticsindex.AnalyticsIndexDAO;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.profiles.IterableObjectInputStream;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

@Named
public class RnaSeqDiffAnalyticsDocumentStreamIndexer {

    private static final Logger LOGGER = LoggerFactory.getLogger(RnaSeqDiffAnalyticsDocumentStreamIndexer.class);

    private final RnaSeqDifferentialAnalyticsInputStreamFactory rnaSeqDifferentialAnalyticsInputStreamFactory;
    private DiffAnalyticsDocumentStreamFactory streamFactory;
    private AnalyticsIndexDAO analyticsIndexDAO;

    @Inject
    public RnaSeqDiffAnalyticsDocumentStreamIndexer(RnaSeqDifferentialAnalyticsInputStreamFactory rnaSeqDifferentialAnalyticsInputStreamFactory, DiffAnalyticsDocumentStreamFactory streamFactory, AnalyticsIndexDAO analyticsIndexDAO) {
        this.rnaSeqDifferentialAnalyticsInputStreamFactory = rnaSeqDifferentialAnalyticsInputStreamFactory;
        this.streamFactory = streamFactory;
        this.analyticsIndexDAO = analyticsIndexDAO;
    }

    public int index(String experimentAccession,
                     ExperimentType experimentType,
                     Set<String> factors,
                     SetMultimap<String, String> conditionSearchTermsByContrastGroupId,
                     ImmutableMap<String, String> ensemblSpeciesGroupedByContrastId,
                     Map<String, Integer> numReplicatesByContrastId,
                     Map<String, String> bioentityIdToIdentifierSearch,
                     int batchSize) {



        try (ObjectInputStream<? extends DifferentialAnalytics> closeableInputStream =  rnaSeqDifferentialAnalyticsInputStreamFactory.create(experimentAccession)) {

            LOGGER.info("Start indexing " + experimentAccession);
            StopWatch stopWatch = new StopWatch(getClass().getSimpleName());
            stopWatch.start();

            IterableObjectInputStream<? extends DifferentialAnalytics> iterableInputStream = new IterableObjectInputStream<>(closeableInputStream);

            DiffAnalyticsDocumentStream analyticsDocuments = streamFactory.create(experimentAccession, experimentType, factors, ensemblSpeciesGroupedByContrastId,
                    iterableInputStream, conditionSearchTermsByContrastGroupId, numReplicatesByContrastId, bioentityIdToIdentifierSearch);

            int count = analyticsIndexDAO.addDocuments(analyticsDocuments, batchSize);

            stopWatch.stop();
            LOGGER.info("Done indexing {}, indexed {} documents in {} seconds", experimentAccession, count, stopWatch.getTotalTimeSeconds());

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
