package uk.ac.ebi.atlas.experimentimport.analyticsindex;

import uk.ac.ebi.atlas.model.experiment.Experiment;
import uk.ac.ebi.atlas.model.analyticsindex.SolrInputDocumentIterable;
import uk.ac.ebi.atlas.model.experiment.baseline.BioentityPropertyName;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.*;

@Named
public class AnalyticsIndexerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AnalyticsIndexerService.class);

    private final SolrClient solrClient;
    private final ExperimentDataPointStreamFactory experimentDataPointStreamFactory;
    private final AnalyticsIndexDocumentValidator analyticsIndexDocumentValidator;

    @Inject
    public AnalyticsIndexerService(@Qualifier("solrClientAnalytics") SolrClient solrClient,
                                   ExperimentDataPointStreamFactory experimentDataPointStreamFactory,
                                   AnalyticsIndexDocumentValidator analyticsIndexDocumentValidator) {
        this.solrClient = solrClient;
        this.experimentDataPointStreamFactory = experimentDataPointStreamFactory;
        this.analyticsIndexDocumentValidator = analyticsIndexDocumentValidator;
    }

    Iterable<SolrInputDocument> solrInputDocuments(Experiment experiment,
                                                   Map<String, Map<BioentityPropertyName, Set<String>>> bioentityIdToIdentifierSearch)
    throws IOException {
        return new SolrInputDocumentIterable(
                experimentDataPointStreamFactory.stream(experiment).iterator(), bioentityIdToIdentifierSearch);

    }

    public int index(Experiment experiment, Map<String,
            Map<BioentityPropertyName, Set<String>>> bioentityIdToIdentifierSearch, int batchSize) {

        List<SolrInputDocument> toLoad = new ArrayList<>(batchSize);
        int addedIntoThisBatch = 0;
        int addedInTotal = 0;
        try {
            Iterator<SolrInputDocument> it = solrInputDocuments(experiment, bioentityIdToIdentifierSearch).iterator();
            while (it.hasNext()) {
                while (addedIntoThisBatch < batchSize && it.hasNext()) {
                    SolrInputDocument analyticsInputDocument = it.next();
                    if (analyticsIndexDocumentValidator.validate(analyticsInputDocument)) {
                        toLoad.add(analyticsInputDocument);
                        addedIntoThisBatch++;
                    }
                }
                if (addedIntoThisBatch > 0) {
                    UpdateResponse r = solrClient.add(toLoad);
                    LOGGER.info("Sent {} documents for {}, qTime:{} ",
                            addedIntoThisBatch,experiment.getAccession(), r.getQTime());
                    addedInTotal += addedIntoThisBatch;
                    addedIntoThisBatch = 0;
                    toLoad = new ArrayList<>(batchSize);
                }
            }
        } catch (IOException| SolrServerException e) {
            LOGGER.error(e.getMessage(), e);
        }
        LOGGER.info("Finished: " + experiment.getAccession());
        return addedInTotal;

    }


    // synchronized necessary because we do an explicit commit here
    public synchronized void deleteExperimentFromIndex(String accession) {
        LOGGER.info("Deleting documents for {}", accession);
        try {
            solrClient.deleteByQuery("experimentAccession:" + accession);
            solrClient.commit();
        } catch (IOException | SolrServerException e) {
            rollBackAndPropagateException(e);
        }
        LOGGER.info("Done deleting documents for {}", accession);
    }

    public synchronized void deleteAll() {
        LOGGER.info("Deleting all documents");
        try {
            solrClient.deleteByQuery("*:*");
            solrClient.commit();
            solrClient.optimize();
        } catch (IOException | SolrServerException e) {
            rollBackAndPropagateException(e);
        }
        LOGGER.info("Done deleting all documents");
    }

    public synchronized void optimize() {
        LOGGER.info("Optimizing index");
        try {
            solrClient.optimize();
        } catch (IOException | SolrServerException e) {
            rollBackAndPropagateException(e);
        }
        LOGGER.info("Index optimized successfully");
    }

    private void rollBackAndPropagateException(Exception exception) {
        try {
            LOGGER.error(exception.getMessage(), exception);
            solrClient.rollback();
            throw new RuntimeException(exception);
        } catch (IOException | SolrServerException e) {
            LOGGER.error(e.getMessage());
        }
    }
}
