package uk.ac.ebi.atlas.bioentity.properties;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import org.apache.solr.client.solrj.SolrQuery;
import uk.ac.ebi.atlas.solr.BioentityPropertyName;
import uk.ac.ebi.atlas.controllers.BioentityNotFoundException;
import uk.ac.ebi.atlas.solr.bioentities.query.BioentitiesSolrClient;
import uk.ac.ebi.atlas.solr.cloud.SolrCloudCollectionProxyFactory;
import uk.ac.ebi.atlas.solr.cloud.fullanalytics.AnalyticsCollectionProxy;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static uk.ac.ebi.atlas.bioentity.properties.BioEntityCardProperties.BIOENTITY_PROPERTY_NAMES;
import static uk.ac.ebi.atlas.solr.BioentityPropertyName.ENSGENE;

@Named
public class BioEntityPropertyDao {

    private final BioentitiesSolrClient solrClient;
    private final AnalyticsCollectionProxy analyticsCollectionProxy;

    @Inject
    public BioEntityPropertyDao(BioentitiesSolrClient gxaSolrClient,
                                SolrCloudCollectionProxyFactory collectionProxyFactory) {
        this.solrClient = gxaSolrClient;
        this.analyticsCollectionProxy = collectionProxyFactory.createAnalyticsCollectionProxy();
    }

    public Set<String> fetchPropertyValuesForGeneId(String identifier, BioentityPropertyName propertyName) {
        return solrClient.getMap(identifier, ImmutableList.of(propertyName)).values().stream()
                .flatMap(Set::stream)
                .collect(Collectors.toSet());
    }
    
    public Set<String> fetchGeneIdsForPropertyValue(BioentityPropertyName bioentityPropertyName,
                                                    String bioentityPropertyValue) {
        return solrClient.getBioentityIdentifiers(bioentityPropertyName, bioentityPropertyValue);
    }

    public Map<BioentityPropertyName, Set<String>> fetchGenePageProperties(String identifier) {
        Map<BioentityPropertyName, Set<String>> propertiesByName =
                solrClient.getMap(identifier, BIOENTITY_PROPERTY_NAMES);

        if (propertiesByName.isEmpty()) {
            if (analyticsCollectionProxy.query(new SolrQuery("bioentity_identifier_search:" + identifier))
                    .getResults().isEmpty()) {
                throw new BioentityNotFoundException("Gene/protein <em>" + identifier + "</em> not found.");
            } else {
                // We can do this because propertiesByName is a HashMap; arguably we should create a copy of the map if
                // we are to inject new entries
                propertiesByName.put(ENSGENE, ImmutableSet.of(identifier));
            }
        }

        return propertiesByName;
    }

}
