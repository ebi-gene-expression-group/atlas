package uk.ac.ebi.atlas.experimentimport.analyticsindex.differential;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.SetMultimap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.util.StopWatch;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.experimentimport.analytics.differential.DifferentialAnalytics;
import uk.ac.ebi.atlas.experimentimport.analytics.differential.microarray.MicroarrayDifferentialAnalyticsInputStreamFactory;
import uk.ac.ebi.atlas.experimentimport.analyticsindex.AnalyticsIndexDAO;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.profiles.IterableObjectInputStream;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

@Named
public class MicroArrayDiffAnalyticsDocumentStreamIndexer {
    private static final Logger LOGGER = LogManager.getLogger(MicroArrayDiffAnalyticsDocumentStreamIndexer.class);

    private final MicroarrayDifferentialAnalyticsInputStreamFactory microarrayDifferentialAnalyticsInputStreamFactory;
    private DiffAnalyticsDocumentStreamFactory streamFactory;
    private AnalyticsIndexDAO analyticsIndexDAO;

    @Inject
    public MicroArrayDiffAnalyticsDocumentStreamIndexer(MicroarrayDifferentialAnalyticsInputStreamFactory microarrayDifferentialAnalyticsInputStreamFactory, DiffAnalyticsDocumentStreamFactory streamFactory, AnalyticsIndexDAO analyticsIndexDAO) {
        this.microarrayDifferentialAnalyticsInputStreamFactory = microarrayDifferentialAnalyticsInputStreamFactory;
        this.streamFactory = streamFactory;
        this.analyticsIndexDAO = analyticsIndexDAO;
    }

    public int index(String experimentAccession,
                     Set<String> arrayDesignAccessions,
                     ExperimentType experimentType,
                     Set<String> factors,
                     SetMultimap<String, String> conditionSearchTermsByContrastGroupId,
                     ImmutableMap<String, String> ensemblSpeciesGroupedByContrastId,
                     Map<String, Integer> numReplicatesByContrastId,
                     Map<String, String> bioentityIdToIdentifierSearch,
                     int batchSize) {


        int count = 0;

        for (String arrayDesignAccession : arrayDesignAccessions) {
            try (ObjectInputStream<? extends DifferentialAnalytics> closeableInputStream =  microarrayDifferentialAnalyticsInputStreamFactory.create(experimentAccession, arrayDesignAccession)) {

                LOGGER.info("Start indexing " + experimentAccession);
                StopWatch stopWatch = new StopWatch(getClass().getSimpleName());
                stopWatch.start();

                IterableObjectInputStream<? extends DifferentialAnalytics> iterableInputStream = new IterableObjectInputStream<>(closeableInputStream);

                DiffAnalyticsDocumentStream analyticsDocuments = streamFactory.create(experimentAccession, experimentType, factors, ensemblSpeciesGroupedByContrastId,
                        iterableInputStream, conditionSearchTermsByContrastGroupId, numReplicatesByContrastId, bioentityIdToIdentifierSearch);

                int arrayDesignAccessionCount = analyticsIndexDAO.addDocuments(analyticsDocuments, batchSize);

                stopWatch.stop();
                LOGGER.info(String.format("Done indexing %s_%s, indexed %,d documents in %s seconds", experimentAccession, arrayDesignAccession, arrayDesignAccessionCount, stopWatch.getTotalTimeSeconds()));

                count += arrayDesignAccessionCount;

            } catch (IOException e) {
                throw new DiffAnalyticsDocumentStreamIndexerException(e);
            }
        }

        return count;
    }

    private class DiffAnalyticsDocumentStreamIndexerException extends RuntimeException {
        public DiffAnalyticsDocumentStreamIndexerException(Exception e) {
            super(e);
        }
    }

}
