package uk.ac.ebi.atlas.search.suggester;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.Suggestion;
import org.springframework.stereotype.Component;
import uk.ac.ebi.atlas.search.SemanticQueryTerm;
import uk.ac.ebi.atlas.solr.bioentities.query.BioentitiesSolrClient;
import uk.ac.ebi.atlas.species.Species;
import uk.ac.ebi.atlas.species.SpeciesFactory;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;

@Component
public class SuggesterDao {
    private final SpeciesFactory speciesFactory;
    private final BioentitiesSolrClient solrClient;

    public SuggesterDao(SpeciesFactory speciesFactory,
                        BioentitiesSolrClient solrClient) {
        this.speciesFactory = speciesFactory;
        this.solrClient = solrClient;
    }

    @Deprecated
    public List<SemanticQueryTerm> fetchPropertySuggestions(String query, int numberOfSuggestions, String... species) {
        return fetchSuggestions("propertySuggester", query, numberOfSuggestions, species);
    }

    @Deprecated
    public List<SemanticQueryTerm> fetchBioentitySuggestions(String query, int numberOfSuggestions, String... species) {
        return fetchSuggestions("bioentitySuggester", query, numberOfSuggestions, species);
    }

    @Deprecated
    private List<SemanticQueryTerm> fetchSuggestions(String suggesterDictionary, String query, int numberOfSuggestions, String... species) {
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setRequestHandler("/suggest")
                .setParam("suggest.dictionary", suggesterDictionary)
                .setParam("suggest.q", query)
                .setParam(
                        "suggest.cfq",
                        Arrays.stream(species)
                                .map(speciesFactory::create)
                                .map(Species::getEnsemblName)
                                .collect(joining(" ")));

        return solrClient.query(solrQuery).getSuggesterResponse().getSuggestions().values().stream()
                .flatMap(List::stream)
                .distinct()
                .limit(numberOfSuggestions)
                .map(suggestion -> SemanticQueryTerm.create(suggestion.getTerm(), suggestion.getPayload()))
                .collect(Collectors.toList());
    }

    public Stream<Suggestion> fetchBioentityProperties(String query,
                                                       int limit,
                                                       boolean highlight,
                                                       Species... species) {
        return highlight ?
                fetchSuggestions("propertySuggester", query, limit, species) :
                fetchSuggestions("propertySuggesterNoHighlight", query, limit, species);
    }

    private Stream<Suggestion> fetchSuggestions(String suggesterDictionary,
                                                String query,
                                                int limit,
                                                Species... species) {
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setRequestHandler("/suggest")
                .setParam("suggest.dictionary", suggesterDictionary)
                // We don’t set suggest.count because we want unique suggestions, see distinct() below
                // .setParam("suggest.count", Integer.toString(limit))
                .setParam("suggest.q", query)
                .setParam(
                        "suggest.cfq",
                        Arrays.stream(species)
                                .map(Species::getEnsemblName)
                                .collect(joining(" ")));

        return solrClient.query(solrQuery).getSuggesterResponse().getSuggestions().values().stream()
                .flatMap(List::stream)
                // The current implementation considers symbols Aspm and ASPM two different suggestions. I dont’t know
                // if that’s good or bad because I dont’t know if to a biologist it’s meaningful (I guess not). If we
                // change it it should be reflected in a story.
                .distinct()
                .limit(limit);
    }
}
