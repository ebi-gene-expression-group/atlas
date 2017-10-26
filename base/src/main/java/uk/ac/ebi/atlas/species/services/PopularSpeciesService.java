package uk.ac.ebi.atlas.species.services;

import com.atlassian.util.concurrent.LazyReference;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.stream.Collectors;

@Named
public class PopularSpeciesService {

    private final PopularSpeciesDao popularSpeciesDao;
    private final LazyReference<List<PopularSpeciesInfo>> sortedList = new LazyReference<List<PopularSpeciesInfo>>() {
        @Override
        protected List<PopularSpeciesInfo> create() throws Exception {
            return popularSpeciesDao.popularSpecies().stream()
                    .sorted(PopularSpeciesInfo.ReverseComparator)
                    .collect(Collectors.toList());
        }
    };

    @Inject
    public PopularSpeciesService(PopularSpeciesDao popularSpeciesDao) {
        this.popularSpeciesDao = popularSpeciesDao;
    }

    public List<PopularSpeciesInfo> getPopularSpecies() {
        return sortedList.get();
    }

    public List<PopularSpeciesInfo> getPopularSpecies(int howMany) {
        return sortedList.get().subList(0, Math.min(sortedList.get().size(), howMany));
    }

    public List<PopularSpeciesInfo> getPopularSpecies(final String kingdom, int howMany) {
        List<PopularSpeciesInfo> filteredList =
                sortedList.get().stream()
                        .filter(speciesInfo -> kingdom.equalsIgnoreCase(speciesInfo.kingdom()))
                        .collect(Collectors.toList());

        return filteredList.subList(0, Math.min(filteredList.size(), howMany));
    }

}
