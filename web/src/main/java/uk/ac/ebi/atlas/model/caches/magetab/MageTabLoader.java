package uk.ac.ebi.atlas.model.caches.magetab;

import uk.ac.ebi.atlas.model.ExperimentRun;

import java.util.Collection;
import java.util.Set;

public interface MageTabLoader {

    Collection<ExperimentRun> getProcessedExperimentRuns();

    Set<String> extractSpecies();

}
