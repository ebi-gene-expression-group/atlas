package uk.ac.ebi.atlas.search;

import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import uk.ac.ebi.atlas.species.Species;
import uk.ac.ebi.atlas.species.SpeciesFactory;

import static com.google.common.collect.ImmutableList.toImmutableList;

// Gets a list of strings from the DAO and transforms it to a list of canonical species names.

@Component
public class FeaturedSpeciesService {
    private final FeaturedSpeciesDao featuredSpeciesDao;
    private final SpeciesFactory speciesFactory;

    public FeaturedSpeciesService(FeaturedSpeciesDao featuredSpeciesDao, SpeciesFactory speciesFactory) {
        this.featuredSpeciesDao = featuredSpeciesDao;
        this.speciesFactory = speciesFactory;
    }

    public ImmutableList<String> getSpeciesNamesSortedByExperimentCount() {
        return featuredSpeciesDao.fetchSpeciesSortedByExperimentCount().stream()
                .map(speciesFactory::create)    // We map first to species to account for possible alternate spellings
                .map(Species::getReferenceName)
                .map(StringUtils::capitalize)
                .distinct()
                .collect(toImmutableList());
    }
}
