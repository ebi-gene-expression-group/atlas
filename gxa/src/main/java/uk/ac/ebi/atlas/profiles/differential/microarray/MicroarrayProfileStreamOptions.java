package uk.ac.ebi.atlas.profiles.differential.microarray;

import uk.ac.ebi.atlas.profiles.differential.DifferentialProfileStreamOptions;

public interface MicroarrayProfileStreamOptions extends DifferentialProfileStreamOptions {

    Iterable<String> getArrayDesignAccessions();
}
