package uk.ac.ebi.atlas.bioentity.properties;

import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;
import uk.ac.ebi.atlas.model.experiment.baseline.BioentityPropertyName;
import uk.ac.ebi.atlas.solr.BioentityType;
import uk.ac.ebi.atlas.controllers.BioentityNotFoundException;
import uk.ac.ebi.atlas.solr.query.BioentitiesSolrClient;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Map;
import java.util.Set;

@Named
public class BioEntityPropertyDao {

    private final BioentitiesSolrClient solrClient;

    @Inject
    public BioEntityPropertyDao(BioentitiesSolrClient gxaSolrClient) {
        this.solrClient = gxaSolrClient;
    }

    public Set<String> fetchPropertyValuesForGeneId(String identifier, BioentityPropertyName propertyName) {
        String _identifier = identifier.replace(":", "\\:").replace("[", "\\[").replace("]", "\\]");

        return FluentIterable.concat(solrClient.getMap(_identifier, ImmutableList.of(propertyName)).values()).toSet();
    }

    public Set<String> fetchGeneIdsForPropertyValue(BioentityPropertyName bioentityPropertyName, String
            bioentityPropertyValue){
        return solrClient.getBioentityIdentifiers(BioentityType.GENE, bioentityPropertyName, bioentityPropertyValue);
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
