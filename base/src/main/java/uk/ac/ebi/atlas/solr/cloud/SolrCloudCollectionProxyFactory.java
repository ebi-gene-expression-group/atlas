package uk.ac.ebi.atlas.solr.cloud;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.springframework.stereotype.Component;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

@Component
public class SolrCloudCollectionProxyFactory {
    private final CloudSolrClient cloudSolrClient;

    public SolrCloudCollectionProxyFactory(CloudSolrClient cloudSolrClient) {
        this.cloudSolrClient = cloudSolrClient;
    }

    public <C extends CollectionProxy> C create(Class<C> type) {
        try {
            Constructor<C> constructor = type.getConstructor(SolrClient.class);
            return constructor.newInstance(cloudSolrClient);
        } catch (NoSuchMethodException |
                InstantiationException |
                IllegalAccessException |
                InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
