package uk.ac.ebi.atlas.statistics;

import com.google.common.collect.ImmutableMap;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import uk.ac.ebi.atlas.experimentimport.ScxaExperimentDao;
import uk.ac.ebi.atlas.search.FeaturedSpeciesDao;
import uk.ac.ebi.atlas.tsne.TSnePlotDao;

import javax.inject.Inject;
import java.util.Map;

@Component
public class StatsService {

    private ScxaExperimentDao scxaExperimentDao;
    private FeaturedSpeciesDao featuredSpeciesDao;
    private TSnePlotDao tSnePlotDao;

    @Inject
    public StatsService(ScxaExperimentDao scxaExperimentDao,
                           FeaturedSpeciesDao featuredSpeciesDao,
                           TSnePlotDao tSnePlotDao) {
        this.scxaExperimentDao = scxaExperimentDao;
        this.featuredSpeciesDao = featuredSpeciesDao;
        this.tSnePlotDao = tSnePlotDao;
    }

    @Cacheable("stats")
    public Map<String, Integer> getStatistics() {
        return ImmutableMap.of(
                "experimentCount", scxaExperimentDao.countExperiments(),
                "speciesCount", featuredSpeciesDao.fetchTotalSpeciesCount(),
                "cellCount", tSnePlotDao.fetchTotalCellCount());
    }
}
