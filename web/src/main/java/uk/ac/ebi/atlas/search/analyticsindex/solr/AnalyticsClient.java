package uk.ac.ebi.atlas.search.analyticsindex.solr;

import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;

@Named
@Scope("singleton")
public class AnalyticsClient {

    private static final Logger LOGGER = Logger.getLogger(AnalyticsClient.class);

    private SolrClient analyticsSolrClient;

    @Inject
    public AnalyticsClient(@Qualifier("analyticsSolrClient") SolrClient analyticsSolrClient) {
        this.analyticsSolrClient = analyticsSolrClient;
    }

    public QueryResponse query(SolrQuery solrQuery) {
        try {
            return analyticsSolrClient.query(solrQuery);
        } catch (SolrServerException | IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw new SolrException(SolrException.ErrorCode.UNKNOWN, e);
        }
    }
}
