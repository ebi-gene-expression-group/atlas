package uk.ac.ebi.atlas.experimentimport.analyticsindex;

import com.google.common.collect.UnmodifiableIterator;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import uk.ac.ebi.atlas.experimentimport.analyticsindex.baseline.BaselineAnalyticsIndexerService;
import uk.ac.ebi.atlas.experimentimport.analyticsindex.differential.RnaSeqDiffAnalyticsIndexerService;
import uk.ac.ebi.atlas.experimentimport.analyticsindex.differential.MicroArrayDiffAnalyticsIndexerService;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.model.analyticsindex.ExperimentDataPoint;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.baseline.BioentityPropertyName;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExperiment;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.*;

@Named
public class AnalyticsIndexerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AnalyticsIndexerService.class);

    private final SolrClient solrClient;
    private final ExperimentDataPointStreamFactory experimentDataPointStreamFactory;
    private final AnalyticsIndexDAO analyticsIndexDAO;
    private final BaselineAnalyticsIndexerService baselineAnalyticsIndexerService;
    private final RnaSeqDiffAnalyticsIndexerService diffAnalyticsIndexerService;
    private final MicroArrayDiffAnalyticsIndexerService microArrayDiffAnalyticsIndexerService;

    @Inject
    public AnalyticsIndexerService(@Qualifier("analyticsSolrClient") SolrClient solrClient,
                                   ExperimentDataPointStreamFactory experimentDataPointStreamFactory,

            AnalyticsIndexDAO analyticsIndexDAO, BaselineAnalyticsIndexerService baselineAnalyticsIndexerService, RnaSeqDiffAnalyticsIndexerService diffAnalyticsIndexerService, MicroArrayDiffAnalyticsIndexerService microArrayDiffAnalyticsIndexerService) {
        this.solrClient = solrClient;
        this.experimentDataPointStreamFactory = experimentDataPointStreamFactory;
        this.analyticsIndexDAO = analyticsIndexDAO;
        this.baselineAnalyticsIndexerService = baselineAnalyticsIndexerService;
        this.diffAnalyticsIndexerService = diffAnalyticsIndexerService;
        this.microArrayDiffAnalyticsIndexerService = microArrayDiffAnalyticsIndexerService;
    }


    Iterable<SolrInputDocument> solrInputDocuments(Experiment experiment, Map<String,Map<BioentityPropertyName,
            Set<String>>> bioentityIdToIdentifierSearch) {
return new SolrInputDocumentIterable(experimentDataPointStreamFactory.stream(experiment).iterator(),
        bioentityIdToIdentifierSearch);

    }

    public int index2(Experiment experiment, Map<String,
            Map<BioentityPropertyName, Set<String>>> bioentityIdToIdentifierSearch) {

        final int BATCH_SIZE = 8000;
        List<SolrInputDocument> toLoad = new ArrayList<>(BATCH_SIZE);
        int addedIntoThisBatch = 0;
        int addedInTotal= 0;
        try {
            Iterator<SolrInputDocument> it = solrInputDocuments(experiment, bioentityIdToIdentifierSearch).iterator();
            while(it.hasNext()){
                while(addedIntoThisBatch<BATCH_SIZE && it.hasNext()){
                    toLoad.add(it.next());
                    addedIntoThisBatch++;
                }
                UpdateResponse r = solrClient.add(toLoad);
                LOGGER.info("Sent {} documents for {}, status: {}, qTime:{} ",
                        addedIntoThisBatch,
                        experiment.getAccession(), r.getStatus(), r.getQTime());
                addedInTotal+=addedIntoThisBatch;
                addedIntoThisBatch=0;
                toLoad = new ArrayList<>(BATCH_SIZE);
            }

        } catch (SolrServerException e) {
            LOGGER.error(e.getMessage());

        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
        LOGGER.info("Finished: "+experiment.getAccession());
        return addedInTotal;

    }

    public int index(Experiment experiment, Map<String, String> bioentityIdToIdentifierSearch, int batchSize) {
       ExperimentType experimentType = experiment.getType();

        if (experimentType.isBaseline()) {
            return baselineAnalyticsIndexerService.index((BaselineExperiment) experiment, bioentityIdToIdentifierSearch, batchSize);
        } else if (experimentType == ExperimentType.RNASEQ_MRNA_DIFFERENTIAL) {
            return diffAnalyticsIndexerService.index((DifferentialExperiment) experiment, bioentityIdToIdentifierSearch, batchSize);
        } else if (experimentType == ExperimentType.MICROARRAY_1COLOUR_MICRORNA_DIFFERENTIAL ||
                   experimentType == ExperimentType.MICROARRAY_1COLOUR_MRNA_DIFFERENTIAL ||
                   experimentType == ExperimentType.MICROARRAY_2COLOUR_MRNA_DIFFERENTIAL) {
            return microArrayDiffAnalyticsIndexerService.index((MicroarrayExperiment) experiment, bioentityIdToIdentifierSearch, batchSize);
        }

        throw new UnsupportedOperationException("No analytics loader for experiment type " + experimentType);
    }

    // synchronized necessary because analyticsIndexDao#delete does an explicit commit
    public synchronized void deleteExperimentFromIndex(String accession) {
        LOGGER.info("Deleting documents for {}", accession);
        analyticsIndexDAO.deleteDocumentsForExperiment(accession);
        LOGGER.info("Done deleting documents for {}", accession);
    }

    public synchronized void deleteAll() {
        LOGGER.info("Deleting all documents");
        analyticsIndexDAO.deleteAllDocuments();
        LOGGER.info("Done deleting all documents");
    }

    public synchronized void optimize() {
        LOGGER.info("Optimizing index");
        analyticsIndexDAO.optimize();
        LOGGER.info("Index optimized successfully");
    }
}
