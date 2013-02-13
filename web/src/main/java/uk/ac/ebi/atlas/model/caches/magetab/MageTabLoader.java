package uk.ac.ebi.atlas.model.caches.magetab;

import uk.ac.ebi.arrayexpress2.magetab.exception.ParseException;
import uk.ac.ebi.atlas.model.ExperimentRun;

import java.io.IOException;
import java.util.Collection;
import java.util.Set;

public interface MageTabLoader {
    Collection<ExperimentRun> extractExperimentRuns() throws IOException, ParseException;

    Set<String> extractSpecies();

}
