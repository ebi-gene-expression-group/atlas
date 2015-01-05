package uk.ac.ebi.atlas.solr.admin.index.analytics;

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
            throw new AnalyticsIndexerException(e);
        }

    }

    private class AnalyticsIndexerException extends RuntimeException {
        public AnalyticsIndexerException(Exception e) {
            super(e);
        }
    }
}
