package uk.ac.ebi.atlas.model.cache.baseline.magetab;

import uk.ac.ebi.atlas.model.baseline.ExperimentRun;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public interface MageTabParser {

    Map<String, ExperimentRun> getProcessedExperimentRuns();

    Set<String> extractSpecies();

    /**
     * @return a Map with key factor type and value factor name
     */
    Map<String, String> getFactorNamesByType();


}
