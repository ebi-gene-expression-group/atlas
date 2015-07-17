package uk.ac.ebi.atlas.experimentimport.analyticsindex;

import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;

@Named
@Scope("prototype")
public class AnalyticsIndexDao {
    private static final Logger LOGGER = Logger.getLogger(AnalyticsIndexDao.class);

    private SolrClient solrClient;

    @Inject
    public AnalyticsIndexDao(@Qualifier("analyticsSolrClient") SolrClient solrClient) {
        this.solrClient = solrClient;
    }

    public void addDocument(AnalyticsDocument analyticsDocument) {
        try {
            solrClient.addBean(analyticsDocument);

            solrClient.commit();
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
                solrClient.addBean(document);
                count++;
            }

            solrClient.commit();
        } catch (Exception e) {
            LOGGER.error(e);
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
            LOGGER.error(e);
            rollBack();
            throw new AnalyticsIndexerException(e);
        }
    }

    private void rollBack() {
        try {
            solrClient.rollback();
        } catch (IOException | SolrServerException e) {
            LOGGER.error(e);
        }
    }

    private class AnalyticsIndexerException extends RuntimeException {
        public AnalyticsIndexerException(Exception e) {
            super(e);
        }
    }

    public long getDocumentCount(String accession) {

        SolrQuery parameters = new SolrQuery();
        parameters.set("q", "experimentAccession:" + accession);
        parameters.set("rows", 0);

        try {
            SolrDocumentList list = solrClient.query(parameters).getResults();
            return list.getNumFound();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SolrServerException e) {
            e.printStackTrace();
        }

        return 0;
    }
}
