package uk.ac.ebi.atlas.solr.admin.index.analytics;

import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;

@Named
@Scope("prototype")
public class AnalyticsIndexer {
    private static final Logger LOGGER = Logger.getLogger(AnalyticsIndexer.class);

    private SolrServer solrServer;

    @Inject
    public AnalyticsIndexer(@Qualifier("analyticsSolrServer") SolrServer solrServer) {
        this.solrServer = solrServer;
    }

    public void addDocument(AnalyticsDocument analyticsDocument) {
        try {
            solrServer.addBean(analyticsDocument);

            solrServer.commit();
        } catch (IOException | SolrServerException e) {
            LOGGER.error(e);
            rollBack();
            throw new AnalyticsIndexerException(e);
        }

    }

    public int addDocuments(Iterable<AnalyticsDocument> documents) {
        int count = 0;
        //TODO: determine best commit size

        try {

            for (AnalyticsDocument document : documents) {
                solrServer.addBean(document);
                count++;
            }

            solrServer.commit();
        } catch (Exception e) {
            LOGGER.error(e);
            rollBack();
            throw new AnalyticsIndexerException(e);
        }

        return count;
    }

    public void deleteDocumentsForExperiment(String accession) {

        try {
            solrServer.deleteByQuery("experimentAccession:" + accession);
            solrServer.commit();
        } catch (IOException | SolrServerException e) {
            LOGGER.error(e);
            rollBack();
            throw new AnalyticsIndexerException(e);
        }
    }

    private void rollBack() {
        try {
            solrServer.rollback();
        } catch (IOException | SolrServerException e) {
            LOGGER.error(e);
        }
    }

    private class AnalyticsIndexerException extends RuntimeException {
        public AnalyticsIndexerException(Exception e) {
            super(e);
        }
    }
}
