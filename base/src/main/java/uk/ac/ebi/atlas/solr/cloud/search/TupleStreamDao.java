package uk.ac.ebi.atlas.solr.cloud.search;

import org.apache.solr.client.solrj.io.stream.TupleStream;

public interface TupleStreamDao {
    TupleStream fetchTupleStream(String zkHost, String collectionNameOrAlias);
}
