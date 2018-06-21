package uk.ac.ebi.atlas.testutils;

import org.springframework.stereotype.Component;
import uk.ac.ebi.atlas.solr.cloud.SolrCloudCollectionProxyFactory;
import uk.ac.ebi.atlas.solr.cloud.fullanalytics.AnalyticsCollectionProxy;
import uk.ac.ebi.atlas.solr.cloud.search.SolrQueryBuilder;

import javax.inject.Inject;

import static uk.ac.ebi.atlas.solr.cloud.fullanalytics.AnalyticsCollectionProxy.BIOENTITY_IDENTIFIER;

@Component
public class SolrUtils {

    @Inject
    private SolrCloudCollectionProxyFactory solrCloudCollectionProxyFactory;

    public String fetchRandomExpressionAtlasGene() {
        AnalyticsCollectionProxy analyticsCollectionProxy = solrCloudCollectionProxyFactory.createAnalyticsCollectionProxy();
        SolrQueryBuilder<AnalyticsCollectionProxy> queryBuilder = new SolrQueryBuilder<>();
        queryBuilder.setFieldList(BIOENTITY_IDENTIFIER);
        queryBuilder.setRows(10000);
        return analyticsCollectionProxy.query(queryBuilder).getResults().get(1000).getFieldValue("bioentity_identifier").toString();
    }
}
