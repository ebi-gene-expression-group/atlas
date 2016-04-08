package uk.ac.ebi.atlas.profiles.differential.microarray;

import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.model.differential.DifferentialExpression;
import uk.ac.ebi.atlas.model.differential.DifferentialProfile;
import uk.ac.ebi.atlas.profiles.differential.DifferentialProfileStreamOptions;

public interface DifferentialProfileStreamFactory<T extends DifferentialProfileStreamOptions, P
 extends DifferentialProfile<? extends DifferentialExpression>> {

    ObjectInputStream<P> create(T options);

}
