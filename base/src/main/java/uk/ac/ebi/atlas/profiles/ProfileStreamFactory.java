package uk.ac.ebi.atlas.profiles;

import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.model.experiment.Experiment;
import uk.ac.ebi.atlas.model.Expression;
import uk.ac.ebi.atlas.model.Profile;
import uk.ac.ebi.atlas.profiles.differential.ProfileStreamOptions;

import java.io.IOException;

public interface ProfileStreamFactory<E extends Experiment, T extends ProfileStreamOptions<X>, P
 extends Profile<X, ? extends Expression>,X> {

    ObjectInputStream<P> create(E experiment, T options) throws IOException;

}
