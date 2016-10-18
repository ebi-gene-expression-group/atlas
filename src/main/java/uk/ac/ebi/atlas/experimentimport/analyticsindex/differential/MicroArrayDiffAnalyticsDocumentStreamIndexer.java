package uk.ac.ebi.atlas.experimentimport.analyticsindex.differential;

import com.google.common.collect.SetMultimap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.experimentimport.analytics.differential.DifferentialAnalytics;
import uk.ac.ebi.atlas.experimentimport.analytics.differential.microarray.MicroarrayDifferentialAnalyticsInputStreamFactory;
import uk.ac.ebi.atlas.experimentimport.analyticsindex.AnalyticsIndexDAO;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.profiles.IterableObjectInputStream;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.Map;

@Named
public class MicroArrayDiffAnalyticsDocumentStreamIndexer {
    private static final Logger LOGGER = LoggerFactory.getLogger(MicroArrayDiffAnalyticsDocumentStreamIndexer.class);

    private final MicroarrayDifferentialAnalyticsInputStreamFactory microarrayDifferentialAnalyticsInputStreamFactory;
    private AnalyticsIndexDAO analyticsIndexDAO;

    @Inject
    public MicroArrayDiffAnalyticsDocumentStreamIndexer(MicroarrayDifferentialAnalyticsInputStreamFactory microarrayDifferentialAnalyticsInputStreamFactory, AnalyticsIndexDAO analyticsIndexDAO) {
        this.microarrayDifferentialAnalyticsInputStreamFactory = microarrayDifferentialAnalyticsInputStreamFactory;
        this.analyticsIndexDAO = analyticsIndexDAO;
    }

    public int index(MicroarrayExperiment experiment,
                     SetMultimap<String, String> conditionSearchTermsByContrastGroupId,
                     Map<String, Integer> numReplicatesByContrastId,
                     Map<String, String> bioentityIdToIdentifierSearch,
                     int batchSize) {


        int count = 0;

        for (String arrayDesignAccession : experiment.getArrayDesignAccessions()) {
            try (ObjectInputStream<? extends DifferentialAnalytics> closeableInputStream =
                         microarrayDifferentialAnalyticsInputStreamFactory.create(experiment.getAccession(), arrayDesignAccession)) {

                LOGGER.info("Start indexing {}", experiment.getAccession());
                StopWatch stopWatch = new StopWatch(getClass().getSimpleName());
                stopWatch.start();

                int arrayDesignAccessionCount = analyticsIndexDAO.addDocuments(new DiffAnalyticsDocumentStream(experiment,new
                        IterableObjectInputStream<>(closeableInputStream), conditionSearchTermsByContrastGroupId,
                        numReplicatesByContrastId, bioentityIdToIdentifierSearch), batchSize);

                stopWatch.stop();
                LOGGER.info("Done indexing {}_{}, indexed {} documents in {} seconds", experiment.getAccession(),
                        arrayDesignAccession,
                        arrayDesignAccessionCount, stopWatch.getTotalTimeSeconds());

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
