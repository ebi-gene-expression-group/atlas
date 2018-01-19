package uk.ac.ebi.atlas.solr.cloud;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.request.UpdateRequest;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

// abstract so that clients are forced to use specific collections
public abstract class CollectionProxy {
    private static final Logger LOGGER = LoggerFactory.getLogger(CollectionProxy.class);

    public final SolrClient solrClient;
    public final String nameOrAlias;

    protected CollectionProxy(SolrClient solrClient, String nameOrAlias) {
        this.solrClient = solrClient;
        this.nameOrAlias = nameOrAlias;
    }

    public QueryResponse query(SolrQuery solrQuery) {
        try {
            // Change maybe to: return new QueryRequest()
            return solrClient.query(nameOrAlias, solrQuery);
        } catch (IOException | SolrServerException e) {
            logException(e);
            throw new IllegalStateException(e);
        }
    }

    public UpdateResponse addAndCommit(Collection<SolrInputDocument> docs) {
        try {
            return new UpdateRequest().add(docs).commit(solrClient, nameOrAlias);
        } catch (IOException | SolrServerException e) {
            logException(e);
            throw new IllegalStateException(e);
        }
    }

    public UpdateResponse deleteAllAndCommit() {
        try {
            return new UpdateRequest().deleteByQuery("*:*").commit(solrClient, nameOrAlias);
        } catch (IOException | SolrServerException e) {
            logException(e);
            throw new IllegalStateException(e);
        }
    }

    private void logException(Exception e) {
        LOGGER.error(
                "Problem connecting to SolrCloud {} with collection {}, full stack trace follows:\n\t{}",
                solrClient.getClass().getSimpleName(),
                nameOrAlias,
                Arrays.stream(e.getStackTrace()).map(StackTraceElement::toString).collect(Collectors.joining("\n\t")));
    }
}
