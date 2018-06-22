package uk.ac.ebi.atlas.solr.cloud.search.streamingexpressions.source;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.io.stream.CloudSolrStream;
import org.apache.solr.client.solrj.io.stream.TupleStream;
import uk.ac.ebi.atlas.solr.cloud.CollectionProxy;
import uk.ac.ebi.atlas.solr.cloud.search.SolrQueryBuilder;
import uk.ac.ebi.atlas.solr.cloud.search.streamingexpressions.TupleStreamBuilder;

import java.io.IOException;
import java.io.UncheckedIOException;

public class SearchStreamBuilder<T extends CollectionProxy> extends TupleStreamBuilder<T> {
    private final T collectionProxy;
    private final SolrQuery solrQuery;

    public SearchStreamBuilder(T collectionProxy, SolrQueryBuilder<T> solrQueryBuilder) {
        this.collectionProxy = collectionProxy;
        solrQuery = solrQueryBuilder.build();
    }

    @Override
    protected TupleStream getRawTupleStream() {
        try {
            // Will throw ClassCastException if SolrClient isn’t CloudSolrClient, beware in testing
            String zkHost = ((CloudSolrClient) collectionProxy.solrClient).getZkHost();
            String collectionNameOrAlias = collectionProxy.nameOrAlias;

            return new CloudSolrStream(zkHost, collectionNameOrAlias, solrQuery);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
