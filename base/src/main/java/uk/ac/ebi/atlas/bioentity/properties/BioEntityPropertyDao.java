package uk.ac.ebi.atlas.bioentity.properties;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import uk.ac.ebi.atlas.controllers.BioentityNotFoundException;
import uk.ac.ebi.atlas.solr.BioentityPropertyName;
import uk.ac.ebi.atlas.solr.bioentities.query.BioentitiesSolrClient;
import uk.ac.ebi.atlas.solr.cloud.SolrCloudCollectionProxyFactory;
import uk.ac.ebi.atlas.solr.cloud.collections.AnalyticsCollectionProxy;
import uk.ac.ebi.atlas.solr.cloud.search.SolrQueryBuilder;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static uk.ac.ebi.atlas.bioentity.properties.BioEntityCardProperties.BIOENTITY_PROPERTY_NAMES;
import static uk.ac.ebi.atlas.solr.BioentityPropertyName.ENSGENE;
import static uk.ac.ebi.atlas.solr.cloud.collections.AnalyticsCollectionProxy.BIOENTITY_IDENTIFIER_SEARCH;

@Named
public class BioEntityPropertyDao {

    private final BioentitiesSolrClient solrClient;
    private final AnalyticsCollectionProxy analyticsCollectionProxy;

    @Inject
    public BioEntityPropertyDao(BioentitiesSolrClient gxaSolrClient,
                                SolrCloudCollectionProxyFactory collectionProxyFactory) {
        this.solrClient = gxaSolrClient;
        this.analyticsCollectionProxy = collectionProxyFactory.create(AnalyticsCollectionProxy.class);
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
            SolrQueryBuilder<AnalyticsCollectionProxy> solrQueryBuilder = new SolrQueryBuilder<>();
            solrQueryBuilder.addQueryFieldByTerm(BIOENTITY_IDENTIFIER_SEARCH, identifier);
            if (analyticsCollectionProxy.query(solrQueryBuilder).getResults().isEmpty()) {
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
