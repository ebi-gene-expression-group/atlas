package uk.ac.ebi.atlas.testutils;

import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.stereotype.Component;
import uk.ac.ebi.atlas.solr.cloud.SolrCloudCollectionProxyFactory;
import uk.ac.ebi.atlas.solr.cloud.collections.AnalyticsCollectionProxy;
import static uk.ac.ebi.atlas.solr.cloud.collections.AnalyticsCollectionProxy.AnalyticsSchemaField;
import uk.ac.ebi.atlas.solr.cloud.search.SolrQueryBuilder;
import java.util.concurrent.ThreadLocalRandom;

//import static uk.ac.ebi.atlas.solr.cloud.collections.AnalyticsCollectionProxy.BIOENTITY_IDENTIFIER;

@Component
public class SolrUtils {
    private static final int MAX_ROWS = 10000;
    private static final ThreadLocalRandom RNG = ThreadLocalRandom.current();

    private AnalyticsCollectionProxy analyticsCollectionProxy;

    public SolrUtils(SolrCloudCollectionProxyFactory solrCloudCollectionProxyFactory) {
        analyticsCollectionProxy = solrCloudCollectionProxyFactory.create(AnalyticsCollectionProxy.class);
    }

    public String fetchRandomGeneIdFromAnalytics() {
        SolrQueryBuilder<AnalyticsCollectionProxy> queryBuilder = new SolrQueryBuilder<>();
        queryBuilder
                .setFieldList(AnalyticsCollectionProxy.BIOENTITY_IDENTIFIER)
                .setRows(MAX_ROWS);

        return analyticsCollectionProxy
                .query(queryBuilder)
                .getResults()
                .get(RNG.nextInt(MAX_ROWS))
                .getFieldValue("bioentity_identifier")
                .toString();
    }

    public String fetchRandomGeneIdFromAnalytics(AnalyticsSchemaField field, String term) {
        SolrQueryBuilder<AnalyticsCollectionProxy> queryBuilder = new SolrQueryBuilder<>();
        queryBuilder
                .addQueryFieldByTerm(field, term)
                .setFieldList(AnalyticsCollectionProxy.BIOENTITY_IDENTIFIER)
                .setRows(MAX_ROWS);

        return analyticsCollectionProxy
                .query(queryBuilder)
                .getResults()
                .get(RNG.nextInt(MAX_ROWS))
                .getFieldValue("bioentity_identifier")
                .toString();
    }

    public String fetchRandomGeneWithoutSymbolFromAnalytics() {
        SolrQuery solrQuery = new SolrQuery("-keyword_symbol:*");
        solrQuery.setRows(MAX_ROWS);
        return analyticsCollectionProxy
                .rawQuery(solrQuery)
                .getResults()
                .get(RNG.nextInt(MAX_ROWS))
                .getFieldValue("bioentity_identifier")
                .toString();
    }
}
