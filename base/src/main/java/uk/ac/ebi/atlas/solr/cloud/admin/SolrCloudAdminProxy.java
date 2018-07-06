package uk.ac.ebi.atlas.solr.cloud.admin;

import org.apache.commons.collections.MapUtils;
import org.apache.solr.client.solrj.SolrRequest;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.request.CollectionAdminRequest;
import org.apache.solr.common.util.NamedList;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class SolrCloudAdminProxy {
    private final CloudSolrClient cloudSolrClient;

    public SolrCloudAdminProxy(CloudSolrClient cloudSolrClient) {
        this.cloudSolrClient = cloudSolrClient;
    }

    public boolean areCollectionsUp(List<String> collectionNames, String... aliasedCollectionNames) throws IOException, SolrServerException {
        List<String> aliases = Arrays.asList(aliasedCollectionNames);
        SolrRequest request = new CollectionAdminRequest.ClusterStatus();

        ArrayList<String> allCollectionNames = new ArrayList<>(collectionNames);

        NamedList<Object> response = cloudSolrClient.request(request);

        // Get real collection names for each alias
        aliases.forEach(alias -> allCollectionNames.add(getCollectionNameForAlias(response, alias)));

        List<String> statuses = allCollectionNames
                .stream()
                .map(collection -> getShardStatusesForCollection(response, collection))
                .flatMap(List::stream)
                .collect(Collectors.toList());

        return statuses.isEmpty();
    }

    // Retrieves the collection name associated with an alias, e.g. the scxa-analytics alias returns scxa-analytics-v2
    private String getCollectionNameForAlias(NamedList<Object> response, String alias) {
        LinkedHashMap aliases = (LinkedHashMap) response.findRecursive("cluster", "aliases");

        Object collectionName = aliases.get(alias);

        if(collectionName != null) {
            return collectionName.toString();
        }
        else {
            throw new RuntimeException("The alias " + alias + " does not match any collections in Solr");
        }
    }

    // Returns a list of statuses that are not "active" for each shard for a Solr collection.
    private List<String> getShardStatusesForCollection(NamedList<Object> response, String collectionName) {
        LinkedHashMap collectionStatus = (LinkedHashMap) response.findRecursive("cluster", "collections", collectionName);

        if (MapUtils.isEmpty(collectionStatus)) {
            throw new RuntimeException("The collection " + collectionName + " does not exist in Solr");
        }
        else {
            Stream<String> collectionShardStates = ((LinkedHashMap) collectionStatus.getOrDefault("shards", Stream.empty()))
                    .values()
                    .stream()
                    .map(x -> ((LinkedHashMap) x).get("state"));

            return collectionShardStates
                    .filter(x -> !x.equalsIgnoreCase("active"))
                    .collect(Collectors.toList());

        }
    }
}
