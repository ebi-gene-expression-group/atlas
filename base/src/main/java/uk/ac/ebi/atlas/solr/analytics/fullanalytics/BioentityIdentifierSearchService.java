package uk.ac.ebi.atlas.solr.analytics.fullanalytics;

import uk.ac.ebi.atlas.solr.cloud.SolrCloudCollectionProxyFactory;
import uk.ac.ebi.atlas.solr.cloud.search.FacetStreamBuilder;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collections;
import java.util.List;

@Named
public class BioentityIdentifierSearchService {

    private final AnalyticsCollectionProxy analyticsCollectionProxy;

    @Inject
    public BioentityIdentifierSearchService(SolrCloudCollectionProxyFactory collectionProxyFactory) {
        analyticsCollectionProxy = collectionProxyFactory.createAnalyticsCollectionProxy();
    }

    List<String> mostSpecificGenes(String experimentAccession, int limit) {
        return Collections.emptyList();
    }

    List<String> mostSpecificGenes(String experimentAccession, double expressionLevel, int limit) {
        return Collections.emptyList();
    }

    List<String> mostExpressedGenes(String experimentAccession, int limit) {
        return Collections.emptyList();
    }

    List<String> mostExpressedGenes(String experimentAccession, double expressionLevel, int limit) {
        return Collections.emptyList();
    }
}
