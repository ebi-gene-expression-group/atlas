package uk.ac.ebi.atlas.solr.cloud.search.streamingexpressions;

import org.apache.solr.client.solrj.io.stream.TupleStream;
import uk.ac.ebi.atlas.solr.cloud.CollectionProxy;

@FunctionalInterface
public interface TupleStreamBuilder<T extends CollectionProxy> {
    TupleStream build();
}
