package uk.ac.ebi.atlas.solr.query;

import org.apache.solr.client.solrj.SolrQuery;
import uk.ac.ebi.atlas.search.SemanticQueryTerm;
import uk.ac.ebi.atlas.species.Species;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.stream.Collectors;

@Named
public class SolrBioentitiesSuggesterService {

    private final BioentitiesSolrClient solrClient;

    @Inject
    public SolrBioentitiesSuggesterService(BioentitiesSolrClient solrClient) {
        this.solrClient = solrClient;
    }

    public List<SemanticQueryTerm> fetchPropertySuggestions(String query, Species species, int numberOfSuggestions) {
        return fetchSuggestions("propertySuggester", query, species, numberOfSuggestions);
    }

    public List<SemanticQueryTerm> fetchBioentitySuggestions(String query, Species species, int numberOfSuggestions) {
        return fetchSuggestions("bioentitySuggester", query, species, numberOfSuggestions);
    }

    private List<SemanticQueryTerm> fetchSuggestions(String suggesterDictionary, String query, Species species, int numberOfSuggestions) {
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setRequestHandler("/suggest")
                .setParam("suggest.dictionary", suggesterDictionary)
                .setParam("suggest.q", query)
                .setParam("suggest.cfq", species.getEnsemblName());

        return solrClient.query(solrQuery).getSuggesterResponse().getSuggestions().values().stream()
                .flatMap(List::stream)
                .distinct()
                .limit(numberOfSuggestions)
                .map(suggestion -> SemanticQueryTerm.create(suggestion.getTerm(), suggestion.getPayload()))
                .collect(Collectors.toList());
    }

}
