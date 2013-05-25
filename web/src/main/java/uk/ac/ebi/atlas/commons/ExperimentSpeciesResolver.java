package uk.ac.ebi.atlas.commons;

import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.geneindex.SolrQueryService;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Properties;

@Named()
@Scope("singleton")
public class ExperimentSpeciesResolver {
    private Properties properties;

    private SolrQueryService solrQueryService;

    @Inject
    public ExperimentSpeciesResolver(@Named("species-experiment")Properties properties, SolrQueryService solrQueryService) {
        this.properties = properties;
        this.solrQueryService = solrQueryService;
    }

    public String getExperimentAccession(String bioentitId) {
        String species = solrQueryService.getSpeciesForPropertyValue(bioentitId);
        return properties.getProperty(species.replace(" ", "_"));
    }
}
