to use this configuration you can replace
solr-4.4.0/example/solr
with a softlink to this solr directory.

Or, even better, don't touch the original solr distribution and simply punt at this solr directory in your start script:
-Dsolr.solr.home=<location of this solr directory>

