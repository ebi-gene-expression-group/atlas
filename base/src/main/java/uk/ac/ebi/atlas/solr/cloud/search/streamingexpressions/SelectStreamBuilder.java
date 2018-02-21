package uk.ac.ebi.atlas.solr.cloud.search.streamingexpressions;

import com.google.common.collect.ImmutableMap;
import org.apache.solr.client.solrj.io.SolrClientCache;
import org.apache.solr.client.solrj.io.stream.SelectStream;
import org.apache.solr.client.solrj.io.stream.StreamContext;
import uk.ac.ebi.atlas.solr.cloud.CollectionProxy;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Map;

public class SelectStreamBuilder<T extends CollectionProxy> implements TupleStreamBuilder<T> {
    private final TupleStreamBuilder<T> tupleStreamBuilder;
    private final ImmutableMap.Builder<String, String> fieldMapsBuilder = ImmutableMap.builder();

    public SelectStreamBuilder(TupleStreamBuilder<T> tupleStreamBuilder) {
        this.tupleStreamBuilder = tupleStreamBuilder;
    }

    // public SelectStreamBuilder<T> addFieldMap(String originalfieldName, String newFieldName) {
    //     fieldMapsBuilder.put(originalfieldName, newFieldName);
    //     return this;
    // }

    // We don’t use SchemaField<T> as keys because field names may have been renamed by a previous select clause or it
    // may be a field with a stream evaluator
    public SelectStreamBuilder<T> addFieldMapping(Map<String, String> fieldNamesMap) {
        fieldMapsBuilder.putAll(fieldNamesMap);
        return this;
    }

    @Override
    public SelectStream build() {
        try {
            SelectStream selectStream = new SelectStream(tupleStreamBuilder.build(), fieldMapsBuilder.build());

            // A pinch of SolrCloud dark magic (unlike FacetStream or RankStream, SelectStream uses StreamContext):
            // https://github.com/apache/lucene-solr/blob/70784f456119e44e936d058c541945ebec0efaff/solr/solrj/src/java/org/apache/solr/client/solrj/io/stream/SelectStream.java#L267
            // See also https://lucidworks.com/2017/12/06/streaming-expressions-in-solrj/

            // Apparently, setting SolrClientCache is only necessary if we’re calling getShards on the stream, which
            // we’re not, but we don’t know if Solr inside will do so at some point
            StreamContext streamContext = new StreamContext();
            streamContext.setSolrClientCache(new SolrClientCache());
            selectStream.setStreamContext(new StreamContext());

            return selectStream;
        } catch (IOException e) {
            // The truth is, this constructor can’t throw an IOException, but the class declaration does... lame
            throw new UncheckedIOException(e);
        }
    }
}
