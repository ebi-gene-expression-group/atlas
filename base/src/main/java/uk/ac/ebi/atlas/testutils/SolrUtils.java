package uk.ac.ebi.atlas.testutils;

import org.springframework.stereotype.Component;
import uk.ac.ebi.atlas.solr.cloud.SolrCloudCollectionProxyFactory;
import uk.ac.ebi.atlas.solr.cloud.collections.AnalyticsCollectionProxy;
import uk.ac.ebi.atlas.solr.cloud.search.SolrQueryBuilder;

import java.util.concurrent.ThreadLocalRandom;

import static uk.ac.ebi.atlas.solr.cloud.collections.AnalyticsCollectionProxy.BIOENTITY_IDENTIFIER;

@Component
public class SolrUtils {
    private static final int MAX_ROWS = 10000;
    private static final ThreadLocalRandom RNG = ThreadLocalRandom.current();

    private SolrCloudCollectionProxyFactory solrCloudCollectionProxyFactory;

    public SolrUtils(SolrCloudCollectionProxyFactory solrCloudCollectionProxyFactory) {
        this.solrCloudCollectionProxyFactory = solrCloudCollectionProxyFactory;
    }

    public String fetchRandomGeneFromAnalytics() {
        AnalyticsCollectionProxy analyticsCollectionProxy =
                solrCloudCollectionProxyFactory.create(AnalyticsCollectionProxy.class);
        SolrQueryBuilder<AnalyticsCollectionProxy> queryBuilder = new SolrQueryBuilder<>();
        queryBuilder.setFieldList(BIOENTITY_IDENTIFIER);
        queryBuilder.setRows(MAX_ROWS);
        return analyticsCollectionProxy
                .query(queryBuilder)
                .getResults()
                .get(RNG.nextInt(MAX_ROWS))
                .getFieldValue("bioentity_identifier")
                .toString();
    }
}
