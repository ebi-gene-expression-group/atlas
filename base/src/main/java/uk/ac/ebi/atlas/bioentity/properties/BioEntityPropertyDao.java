package uk.ac.ebi.atlas.bioentity.properties;

import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;
import uk.ac.ebi.atlas.model.baseline.BioentityPropertyName;
import uk.ac.ebi.atlas.solr.query.GxaSolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.atlas.solr.query.BioentityNotFoundException;
import uk.ac.ebi.atlas.solr.query.builders.SolrQueryBuilderFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Map;
import java.util.Set;

@Named
public class BioEntityPropertyDao {

    private final GxaSolrClient solrClient;

    @Inject
    public BioEntityPropertyDao(GxaSolrClient gxaSolrClient) {
        this.solrClient = gxaSolrClient;
    }

    public Set<String> fetchPropertyValuesForGeneId(String identifier, BioentityPropertyName propertyName) {
        String _identifier = identifier.replace(":", "\\:").replace("[", "\\[").replace("]", "\\]");

        return FluentIterable.concat(solrClient.getMap(_identifier, ImmutableList.of(propertyName)).values()).toSet();

    }

    public Map<BioentityPropertyName, Set<String>> fetchTooltipProperties(String identifier) {
        return solrClient.getMap(identifier, ImmutableList.of(BioentityPropertyName.SYNONYM, BioentityPropertyName.GOTERM, BioentityPropertyName.INTERPROTERM));

    }

    public Map<BioentityPropertyName, Set<String>> fetchGenePageProperties(String identifier) {
        Map<BioentityPropertyName, Set<String>> propertiesByName = solrClient.getMap(identifier);
        if (propertiesByName.isEmpty()) {
            throw new BioentityNotFoundException("Gene/protein with accession : " + identifier + " is not found!");
        }
        return propertiesByName;
    }


}
