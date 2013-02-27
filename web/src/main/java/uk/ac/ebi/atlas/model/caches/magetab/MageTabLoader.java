package uk.ac.ebi.atlas.model.caches.magetab;

import uk.ac.ebi.atlas.model.ExperimentRun;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public interface MageTabLoader {

    Collection<ExperimentRun> getProcessedExperimentRuns();

    Set<String> extractSpecies();

    /**
     * @return a Map with key factor type and value factor name
     */
    Map<String, String> getFactorNamesByType();


}
