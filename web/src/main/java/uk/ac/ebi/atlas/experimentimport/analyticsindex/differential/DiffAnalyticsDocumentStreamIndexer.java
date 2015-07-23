package uk.ac.ebi.atlas.experimentimport.analyticsindex.differential;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.SetMultimap;
import org.apache.log4j.Logger;
import org.springframework.util.StopWatch;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.experimentimport.analytics.differential.DifferentialAnalytics;
import uk.ac.ebi.atlas.experimentimport.analytics.differential.rnaseq.RnaSeqDifferentialAnalyticsInputStreamFactory;
import uk.ac.ebi.atlas.experimentimport.analyticsindex.AnalyticsIndexDao;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.profiles.IterableObjectInputStream;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

@Named
public class DiffAnalyticsDocumentStreamIndexer {
    private static final Logger LOGGER = Logger.getLogger(DiffAnalyticsDocumentStreamIndexer.class);

    private final RnaSeqDifferentialAnalyticsInputStreamFactory rnaSeqDifferentialAnalyticsInputStreamFactory;
    private DiffAnalyticsDocumentStreamFactory streamFactory;
    private AnalyticsIndexDao analyticsIndexDao;

    @Inject
    public DiffAnalyticsDocumentStreamIndexer(RnaSeqDifferentialAnalyticsInputStreamFactory rnaSeqDifferentialAnalyticsInputStreamFactory, DiffAnalyticsDocumentStreamFactory streamFactory, AnalyticsIndexDao analyticsIndexDao) {
        this.rnaSeqDifferentialAnalyticsInputStreamFactory = rnaSeqDifferentialAnalyticsInputStreamFactory;
        this.streamFactory = streamFactory;
        this.analyticsIndexDao = analyticsIndexDao;
    }

    public int index(String experimentAccession,
                     ExperimentType experimentType,
                     Set<String> factors,
                     SetMultimap<String, String> conditionSearchTermsByContrastGroupId,
                     ImmutableMap<String, String> ensemblSpeciesGroupedByContrastId,
                     Map<String, Integer> numReplicatesByContrastId,
                     @Nullable Integer batchSize) {



        try (ObjectInputStream<? extends DifferentialAnalytics> closeableInputStream =  rnaSeqDifferentialAnalyticsInputStreamFactory.create(experimentAccession)) {

            LOGGER.info("Start indexing " + experimentAccession);
            StopWatch stopWatch = new StopWatch(getClass().getSimpleName());
            stopWatch.start();

            IterableObjectInputStream<? extends DifferentialAnalytics> iterableInputStream = new IterableObjectInputStream<>(closeableInputStream);

            DiffAnalyticsDocumentStream analyticsDocuments = streamFactory.create(experimentAccession, experimentType, factors, ensemblSpeciesGroupedByContrastId,
                    iterableInputStream, conditionSearchTermsByContrastGroupId, numReplicatesByContrastId);

            int count = analyticsIndexDao.addDocuments(analyticsDocuments, batchSize);

            stopWatch.stop();
            LOGGER.info(String.format("Done indexing %s, indexed %,d documents in %s seconds", experimentAccession, count, stopWatch.getTotalTimeSeconds()));

            return count;

        } catch (IOException e) {
            throw new DiffAnalyticsDocumentStreamIndexerException(e);
        }

    }

    private class DiffAnalyticsDocumentStreamIndexerException extends RuntimeException {
        public DiffAnalyticsDocumentStreamIndexerException(Exception e) {
            super(e);
        }
    }

}
