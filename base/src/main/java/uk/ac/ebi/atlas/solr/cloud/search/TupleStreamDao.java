package uk.ac.ebi.atlas.solr.cloud.search;

import uk.ac.ebi.atlas.solr.cloud.TupleStreamAutoCloseableIterator;

@FunctionalInterface
public interface TupleStreamDao {
    TupleStreamAutoCloseableIterator fetchTupleStream();
}
