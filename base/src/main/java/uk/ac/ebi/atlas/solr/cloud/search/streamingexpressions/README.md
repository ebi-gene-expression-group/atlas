# [Source Streaming Expressions](https://lucene.apache.org/solr/guide/7_1/stream-source-reference.html)
The following line will throw a `ClassCastException` if `solrClient` isn’t a `CloudSolrClient`, so beware in 
testing:

    String zkHost = ((CloudSolrClient) collectionProxy.solrClient).getZkHost();

# [Decorator Streaming Expressions](https://lucene.apache.org/solr/guide/7_1/stream-decorator-reference.html)
It’s safe not to use `try-with-resources` internally when getting the `TupleStream`, since backing streams each 
of them uses are closed in their respective `close()` method.

We don’t use `SchemaField<T>` as keys because field names may have been renamed by a previous select clause or 
it may be a field with a stream evaluator.

It doesn’t make sense for these types to be associated to a `CollectionProxy`, since the source of the 
`TupleStream` may come from different collections (e.g. inner join or intersect) or fields gone through a 
select and their names have been replaced by some arbitrary string.