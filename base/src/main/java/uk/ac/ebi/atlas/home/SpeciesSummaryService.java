package uk.ac.ebi.atlas.home;

import com.atlassian.util.concurrent.LazyReference;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import java.util.Map;

import static com.google.common.collect.ImmutableList.toImmutableList;
import static com.google.common.collect.ImmutableMap.toImmutableMap;
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

    public ImmutableMap<String, ImmutableList<SpeciesSummary>> getSpeciesSummaryGroupedByKingdom(int limit) {
        return popularSpeciesInfos.get().stream()
                        .collect(groupingBy(SpeciesSummary::getKingdom))
                        .entrySet().stream()
                        .collect(toImmutableMap(
                                Map.Entry::getKey,
                                entry -> entry.getValue().stream().limit(limit).collect(toImmutableList())));
    }
}
