package uk.ac.ebi.atlas.species.services;

import java.util.List;
import java.util.stream.Collectors;

public class PopularSpeciesService {

    protected final PopularSpeciesDao popularSpeciesDao;
    protected final List<PopularSpeciesInfo> popularSpeciesInfos;

    public PopularSpeciesService(PopularSpeciesDao popularSpeciesDao) {
        this.popularSpeciesDao = popularSpeciesDao;
        this.popularSpeciesInfos = popularSpeciesDao.getExperimentCountBySpecies().stream()
                .sorted(PopularSpeciesInfo.BY_SIZE_DESCENDING)
                .collect(Collectors.toList());
    }

    public List<PopularSpeciesInfo> getPopularSpecies() {
        return popularSpeciesInfos;
    }

    public List<PopularSpeciesInfo> getPopularSpecies(int howMany) {
        return getSublist(popularSpeciesInfos, howMany);
    }

    public List<PopularSpeciesInfo> getPopularSpecies(final String kingdom, int howMany) {
        List<PopularSpeciesInfo> filteredList =
                popularSpeciesInfos.stream()
                        .filter(speciesInfo -> kingdom.equalsIgnoreCase(speciesInfo.kingdom()))
                        .collect(Collectors.toList());

        return getSublist(filteredList, howMany);
    }

    private List<PopularSpeciesInfo> getSublist(List<PopularSpeciesInfo> list, int length) {
        return list.subList(0, Math.min(list.size(), length));
    }
}
