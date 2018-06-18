package uk.ac.ebi.atlas.solr.cloud.admin;

import org.apache.solr.client.solrj.SolrRequest;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.request.CollectionAdminRequest;
import org.apache.solr.common.util.NamedList;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class SolrCloudAdminProxy {
    private final CloudSolrClient cloudSolrClient;

    public SolrCloudAdminProxy(@Qualifier("zkHost") String zkHost) {
        cloudSolrClient = new CloudSolrClient.Builder().withZkHost(zkHost).build();
    }

    // TODO should implement a method that checks multiple collections at the same time
    public boolean isCollectionUp(String collectionName, boolean isAlias) throws IOException, SolrServerException {
        SolrRequest request = new CollectionAdminRequest.ClusterStatus();

        final NamedList<Object> response = cloudSolrClient.request(request);

        if(isAlias) {
            collectionName = getCollectionNameForAlias(response, collectionName);
        }

        LinkedHashMap collectionStatus = (LinkedHashMap) response.findRecursive("cluster", "collections", collectionName);

        Stream<String> collectionShardStates = ((LinkedHashMap) collectionStatus.get("shards")).values().stream().map(x -> ((LinkedHashMap) x).get("state"));

        List<String> statuses = collectionShardStates.filter(x -> !x.equalsIgnoreCase("active")).collect(Collectors.toList());

        return statuses.isEmpty();
    }

    private String getCollectionNameForAlias(NamedList<Object> queryResponse, String alias) {
        LinkedHashMap aliases = (LinkedHashMap) queryResponse.findRecursive("cluster", "aliases");

        return aliases.get(alias).toString();
    }
}
