package uk.ac.ebi.atlas.home;

import com.atlassian.util.concurrent.LazyReference;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSortedMap;

import java.util.Comparator;

import static com.google.common.collect.ImmutableList.toImmutableList;
import static com.google.common.collect.ImmutableSortedMap.toImmutableSortedMap;
import static java.util.stream.Collectors.groupingBy;

public class SpeciesSummaryService {
    private final SpeciesSummaryDao speciesSummaryDao;
    private final LazyReference<ImmutableList<SpeciesSummary>> popularSpeciesInfos =
            new LazyReference<ImmutableList<SpeciesSummary>>() {
                @Override
                protected ImmutableList<SpeciesSummary> create() {
                    return speciesSummaryDao.getExperimentCountBySpecies().stream()
                            .sorted(SpeciesSummary.BY_SIZE_DESCENDING)
                            .collect(toImmutableList());
                }
            };

    public SpeciesSummaryService(SpeciesSummaryDao speciesSummaryDao) {
        this.speciesSummaryDao = speciesSummaryDao;
    }

    public ImmutableSortedMap<String, ImmutableList<SpeciesSummary>> getSpeciesSummaryGroupedByKingdom(int limit) {
        return popularSpeciesInfos.get().stream()
                        .collect(groupingBy(SpeciesSummary::getKingdom))
                        .entrySet().stream()
                        .collect(toImmutableSortedMap(
                                new ArbitraryStringComparator("animals", "plants", "fungi", "protists"),
                                entry -> entry.getKey(),
                                entry -> entry.getValue().stream().limit(limit).collect(toImmutableList())));
    }

    private static class ArbitraryStringComparator implements Comparator<String> {
        private final ImmutableList<String> stringsInOrder;

        private ArbitraryStringComparator(String... stringsInOrder) {
            this.stringsInOrder = ImmutableList.copyOf(stringsInOrder);
        }

        @Override
        public int compare(String o1, String o2) {
            if (stringsInOrder.contains(o1) && stringsInOrder.contains(o2)) {
                return stringsInOrder.indexOf(o1) - stringsInOrder.indexOf(o2);
            }
            if (stringsInOrder.contains(o1)) {  // && !stringsInOrder.contains(o2)
                return -1;
            }
            if (stringsInOrder.contains(o2)) {  // && !stringsInOrder.contains(o1)
                return 1;
            }
            return o1.compareTo(o2);            //!stringsInOrder.contains(o1) && !stringsInOrder.contains(o2)
        }
    }
}
