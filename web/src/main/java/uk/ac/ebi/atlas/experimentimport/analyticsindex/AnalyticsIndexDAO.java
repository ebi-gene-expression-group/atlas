package uk.ac.ebi.atlas.experimentimport.analyticsindex;

import com.google.common.collect.ImmutableList;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

@Named
@Scope("prototype")
public class AnalyticsIndexDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(AnalyticsIndexDAO.class);

    private SolrClient solrClient;

    private static final int COMMIT_TIME_IN_MILLISECONDS = 15 * 60 * 1000;  // 15 minutes

    @Inject
    public AnalyticsIndexDAO(@Qualifier("analyticsSolrClient") SolrClient solrClient) {
        this.solrClient = solrClient;
    }

    public int addDocuments(Iterable<AnalyticsDocument> documents, int batchSize) {
        int count = 0;

        try {
            Iterator<AnalyticsDocument> iterator = documents.iterator();

            ImmutableList.Builder<AnalyticsDocument> builder;
            int thisBatchSize;

            while (iterator.hasNext()) {
                builder = new ImmutableList.Builder<>();
                thisBatchSize = 0;
                while (iterator.hasNext() && thisBatchSize < batchSize) {
                    builder.add(iterator.next());
                    thisBatchSize++;
                }
                solrClient.addBeans(builder.build(), COMMIT_TIME_IN_MILLISECONDS);
                count += thisBatchSize;
            }

            solrClient.commit(false, false);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            rollBack();
            throw new AnalyticsIndexerException(e);
        }

        return count;
    }

    public void deleteDocumentsForExperiment(String accession) {
        try {
            solrClient.deleteByQuery("experimentAccession:" + accession);
            solrClient.commit();
        } catch (IOException | SolrServerException e) {
            LOGGER.error(e.getMessage(), e);
            rollBack();
            throw new AnalyticsIndexerException(e);
        }
    }

    private void rollBack() {
        try {
            solrClient.rollback();
        } catch (IOException | SolrServerException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    private class AnalyticsIndexerException extends RuntimeException {
        public AnalyticsIndexerException(Exception e) {
            super(e);
        }
    }
}
