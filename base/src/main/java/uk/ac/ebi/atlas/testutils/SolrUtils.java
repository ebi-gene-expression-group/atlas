package uk.ac.ebi.atlas.testutils;

import org.springframework.stereotype.Component;
import uk.ac.ebi.atlas.solr.cloud.SolrCloudCollectionProxyFactory;
import uk.ac.ebi.atlas.solr.cloud.collections.AnalyticsCollectionProxy;
import uk.ac.ebi.atlas.solr.cloud.search.SolrQueryBuilder;

import java.util.Random;

import static uk.ac.ebi.atlas.solr.cloud.collections.AnalyticsCollectionProxy.BIOENTITY_IDENTIFIER;

@Component
public class SolrUtils {
    private SolrCloudCollectionProxyFactory solrCloudCollectionProxyFactory;

    public SolrUtils(SolrCloudCollectionProxyFactory solrCloudCollectionProxyFactory) {
        this.solrCloudCollectionProxyFactory = solrCloudCollectionProxyFactory;
    }

    private Random geneNumber = new Random();

    public String fetchRandomGeneFromAnalytics() {
        AnalyticsCollectionProxy analyticsCollectionProxy = solrCloudCollectionProxyFactory.create(AnalyticsCollectionProxy.class);
        SolrQueryBuilder<AnalyticsCollectionProxy> queryBuilder = new SolrQueryBuilder<>();
        queryBuilder.setFieldList(BIOENTITY_IDENTIFIER);
        queryBuilder.setRows(10000);
        return analyticsCollectionProxy.query(queryBuilder).getResults().get(geneNumber.nextInt(9999)).getFieldValue("bioentity_identifier").toString();
    }
}
