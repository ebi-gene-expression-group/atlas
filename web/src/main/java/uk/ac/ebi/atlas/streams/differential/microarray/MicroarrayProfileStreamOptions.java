package uk.ac.ebi.atlas.streams.differential.microarray;

import uk.ac.ebi.atlas.streams.differential.DifferentialProfileStreamOptions;

public interface MicroarrayProfileStreamOptions extends DifferentialProfileStreamOptions {

    Iterable<String> getArrayDesignAccessions();
}
