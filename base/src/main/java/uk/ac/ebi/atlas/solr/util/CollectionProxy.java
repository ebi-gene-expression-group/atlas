package uk.ac.ebi.atlas.solr.util;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

// abstract so that it can be generalised to the bioentities collection if convenient
public abstract class CollectionProxy {
    private static final Logger LOGGER = LoggerFactory.getLogger(CollectionProxy.class);

    private final SolrClient solrClient;
    private final String collection;

    CollectionProxy(SolrClient solrClient, String collection) {
        this.solrClient = solrClient;
        this.collection = collection;
    }

    public QueryResponse query(SolrQuery solrQuery) {
        try {
            return solrClient.query(collection, solrQuery);
        } catch (IOException | SolrServerException e) {
            logException(e);
            throw new IllegalStateException(e);
        }
    }

    public Pair<UpdateResponse, UpdateResponse> addAndCommit(Collection<SolrInputDocument> docs) {
        try {
            UpdateResponse addUpdateResponse = add(docs);
            UpdateResponse commitUpdateResponse = solrClient.commit();
            return Pair.of(addUpdateResponse, commitUpdateResponse);
        } catch (IOException | SolrServerException e) {
            logException(e);
            throw new IllegalStateException(e);
        }
    }

    public UpdateResponse add(Collection<SolrInputDocument> docs) {
        try {
            return solrClient.add(collection, docs);
        } catch (IOException | SolrServerException e) {
            logException(e);
            throw new IllegalStateException(e);
        }
    }

    private void logException(Exception e) {
        LOGGER.error(
                "Problem connecting to SolrCloud {} with collection {}, full stack trace follows:\n\t{}",
                solrClient.getClass().getSimpleName(),
                collection,
                Arrays.stream(e.getStackTrace()).map(StackTraceElement::toString).collect(Collectors.joining("\n\t")));
    }
}
