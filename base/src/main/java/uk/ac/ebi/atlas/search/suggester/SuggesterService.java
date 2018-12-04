package uk.ac.ebi.atlas.search.suggester;

import com.google.common.collect.ImmutableMap;
import org.apache.solr.client.solrj.response.Suggestion;
import org.springframework.stereotype.Component;
import uk.ac.ebi.atlas.species.Species;
import uk.ac.ebi.atlas.species.SpeciesFactory;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

@Component
public class SuggesterService {
    // Remember that suggestions are distinct()’ed, so this value is an upper bound
    public static final int DEFAULT_MAX_NUMBER_OF_SUGGESTIONS = 10;

    private static final Function<Suggestion, Map<String, String>>
    SUGGESTION_TO_MAP =
            suggestion -> ImmutableMap.of("term", suggestion.getTerm(), "category", suggestion.getPayload());

    private final SuggesterDao suggesterDao;
    private final SpeciesFactory speciesFactory;

    public SuggesterService(SuggesterDao suggesterDao, SpeciesFactory speciesFactory) {
        this.suggesterDao = suggesterDao;
        this.speciesFactory = speciesFactory;
    }

    public Stream<Map<String, String>> fetchPropertiesWithoutHighlighting(String query, String...  species) {
        Species[] speciesArray = Arrays.stream(species).map(speciesFactory::create).toArray(Species[]::new);

        return suggesterDao.fetchBioentityProperties(query, DEFAULT_MAX_NUMBER_OF_SUGGESTIONS, false, speciesArray)
                .map(SUGGESTION_TO_MAP);
    }

    // Like the above but highlights (in HTML bold <b>...</b>) the matched region of the suggestion
    public Stream<Map<String, String>> fetchPropertiesWithHighlighting(String query, String...  species) {
        Species[] speciesArray = Arrays.stream(species).map(speciesFactory::create).toArray(Species[]::new);

        return suggesterDao.fetchBioentityProperties(query, DEFAULT_MAX_NUMBER_OF_SUGGESTIONS, true, speciesArray)
                .map(SUGGESTION_TO_MAP);
    }

    public Stream<Map<String, String>> fetchIdentifiers(String query, String...  species) {
        Species[] speciesArray = Arrays.stream(species).map(speciesFactory::create).toArray(Species[]::new);

        return suggesterDao.fetchBioentityIdentifiers(query, DEFAULT_MAX_NUMBER_OF_SUGGESTIONS, speciesArray)
                .map(SUGGESTION_TO_MAP);
    }
}
