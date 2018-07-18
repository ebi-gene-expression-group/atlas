package uk.ac.ebi.atlas.search.suggester;

import com.google.common.collect.ImmutableMap;
import org.springframework.stereotype.Component;
import uk.ac.ebi.atlas.species.Species;
import uk.ac.ebi.atlas.species.SpeciesFactory;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Stream;

@Component
public class SuggesterService {
    // Remember that suggestions are distinct()’ed, so this value is an upper bound
    private static final int DEFAULT_MAX_NUMBER_OF_SUGGESTIONS = 20;

    private final SuggesterDao suggesterDao;
    private final SpeciesFactory speciesFactory;

    public SuggesterService(SuggesterDao suggesterDao, SpeciesFactory speciesFactory) {
        this.suggesterDao = suggesterDao;
        this.speciesFactory = speciesFactory;
    }

    public Stream<Map<String, String>> fetchGroupedIdSuggestions(String query, String...  species) {
        Species[] _species = Arrays.stream(species).map(speciesFactory::create).toArray(Species[]::new);

        return suggesterDao.fetchBioentityProperties(query, DEFAULT_MAX_NUMBER_OF_SUGGESTIONS, false, _species)
                .map(suggestion -> ImmutableMap.of("term", suggestion.getTerm(), "category", suggestion.getPayload()));
    }
}
