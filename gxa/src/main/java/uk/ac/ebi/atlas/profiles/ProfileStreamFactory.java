package uk.ac.ebi.atlas.profiles;

import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.model.Expression;
import uk.ac.ebi.atlas.model.Profile;
import uk.ac.ebi.atlas.profiles.differential.ProfileStreamOptions;

public interface ProfileStreamFactory<T extends ProfileStreamOptions<X>, P
 extends Profile<X, ? extends Expression>,X> {

    ObjectInputStream<P> create(T options);

}
