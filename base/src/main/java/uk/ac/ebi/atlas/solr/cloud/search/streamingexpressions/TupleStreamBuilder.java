package uk.ac.ebi.atlas.solr.cloud.search.streamingexpressions;

import org.apache.solr.client.solrj.io.stream.StreamContext;
import org.apache.solr.client.solrj.io.stream.TupleStream;
import uk.ac.ebi.atlas.solr.cloud.CollectionProxy;

import static uk.ac.ebi.atlas.configuration.SolrConfig.SOLR_CLIENT_CACHE;

public abstract class TupleStreamBuilder<T extends CollectionProxy> {
    protected abstract TupleStream getRawTupleStream();

    public TupleStream build() {
        TupleStream tupleStream = getRawTupleStream();

        // WARNING: returning TupleStream as-is without calling this method can result in an NPE
        //
        // Some TupleStream subclasses use the StreamContext internally (e.g. SelectStream) whereas others don’t (e.g.
        // FacetStream, RankStream). In these latter cases, apparently, setting a StreamContext with a SolrClientCache
        // is only necessary if we’re calling getShards on the stream (see source for TupleStream), but we don’t know
        // if SolrJ will do so at some point or there are other uses, so we do the safe thing.

        StreamContext streamContext = new StreamContext();
        streamContext.setSolrClientCache(SOLR_CLIENT_CACHE);
        tupleStream.setStreamContext(streamContext);

        return tupleStream;
    }
}
