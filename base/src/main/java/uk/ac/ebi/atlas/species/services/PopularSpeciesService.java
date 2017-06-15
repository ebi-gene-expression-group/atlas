package uk.ac.ebi.atlas.species.services;

import com.atlassian.util.concurrent.LazyReference;
import com.google.common.collect.FluentIterable;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
public class PopularSpeciesService {

    private final PopularSpeciesDao popularSpeciesDao;
    private final LazyReference<List<PopularSpeciesInfo>> sortedList = new LazyReference<List<PopularSpeciesInfo>>() {
        @Override
        protected List<PopularSpeciesInfo> create() throws Exception {
            return FluentIterable.from(popularSpeciesDao.popularSpecies()).toSortedList(PopularSpeciesInfo.ReverseComparator);
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
                FluentIterable.from(sortedList.get()).filter(input -> {
                    return kingdom.equalsIgnoreCase(input.kingdom());
                }).toList();

        return filteredList.subList(0, Math.min(filteredList.size(), howMany));
    }

}
