package uk.ac.ebi.atlas.streams.differential;

import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.model.differential.DifferentialProfile;
import uk.ac.ebi.atlas.model.differential.Regulation;

public interface DifferentialProfileStreamFactory<T extends DifferentialProfile> {

    ObjectInputStream<T> create(String experimentAccession, double cutOff, Regulation regulation);

}
