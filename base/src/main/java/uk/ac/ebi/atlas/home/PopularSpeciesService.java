package uk.ac.ebi.atlas.home;

import com.atlassian.util.concurrent.LazyReference;

import java.util.List;
import java.util.stream.Collectors;

public class PopularSpeciesService {

    protected final PopularSpeciesDao popularSpeciesDao;
    private final LazyReference<List<PopularSpeciesInfo>> popularSpeciesInfos = new LazyReference<List<PopularSpeciesInfo>>() {
        @Override
        protected List<PopularSpeciesInfo> create() {
            return popularSpeciesDao.getExperimentCountBySpecies().stream()
                    .sorted(PopularSpeciesInfo.BY_SIZE_DESCENDING)
                    .collect(Collectors.toList());
        }
    };

    public PopularSpeciesService(PopularSpeciesDao popularSpeciesDao) {
        this.popularSpeciesDao = popularSpeciesDao;
    }

    public List<PopularSpeciesInfo> getPopularSpecies() {
        return popularSpeciesInfos.get();
    }

    public List<PopularSpeciesInfo> getPopularSpecies(int howMany) {
        return getSublist(popularSpeciesInfos.get(), howMany);
    }

    public List<PopularSpeciesInfo> getPopularSpecies(final String kingdom, int howMany) {
        List<PopularSpeciesInfo> filteredList =
                popularSpeciesInfos.get().stream()
                        .filter(speciesInfo -> kingdom.equalsIgnoreCase(speciesInfo.kingdom()))
                        .collect(Collectors.toList());

        return getSublist(filteredList, howMany);
    }

    private List<PopularSpeciesInfo> getSublist(List<PopularSpeciesInfo> list, int length) {
        return list.subList(0, Math.min(list.size(), length));
    }
}
