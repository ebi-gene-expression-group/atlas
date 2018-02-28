package uk.ac.ebi.atlas.bioentity.properties;

import com.google.common.collect.ImmutableList;
import uk.ac.ebi.atlas.model.experiment.baseline.BioentityPropertyName;
import uk.ac.ebi.atlas.controllers.BioentityNotFoundException;
import uk.ac.ebi.atlas.solr.bioentities.query.BioentitiesSolrClient;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Named
public class BioEntityPropertyDao {

    private final BioentitiesSolrClient solrClient;

    @Inject
    public BioEntityPropertyDao(BioentitiesSolrClient gxaSolrClient) {
        this.solrClient = gxaSolrClient;
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

    public Map<BioentityPropertyName, Set<String>> fetchTooltipProperties(String identifier) {
        return solrClient.getMap(
                identifier,
                ImmutableList.of(
                        BioentityPropertyName.SYNONYM,
                        BioentityPropertyName.GOTERM,
                        BioentityPropertyName.INTERPROTERM));
    }

    public Map<BioentityPropertyName, Set<String>> fetchGenePageProperties(String identifier) {
        Map<BioentityPropertyName, Set<String>> propertiesByName = solrClient.getMap(identifier, BioEntityCardProperties.bioentityPropertyNames);
        if (propertiesByName.isEmpty()) {
            throw new BioentityNotFoundException("Gene/protein <em>" + identifier + "</em> not found.");
        }
        return propertiesByName;
    }

}
