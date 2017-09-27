package uk.ac.ebi.atlas.profiles.stream;

import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.model.DescribesDataColumns;
import uk.ac.ebi.atlas.model.Expression;
import uk.ac.ebi.atlas.model.Profile;
import uk.ac.ebi.atlas.model.experiment.Experiment;
import uk.ac.ebi.atlas.profiles.differential.ProfileStreamOptions;

import java.util.Collection;
import java.util.Optional;

public interface CreatesProfileStream<DataColumnDescriptor extends DescribesDataColumns, Expr extends Expression,
        E extends Experiment<DataColumnDescriptor>, StreamOptions extends ProfileStreamOptions<DataColumnDescriptor>,
        Prof extends Profile<DataColumnDescriptor, Expr, Prof>>  {

    ObjectInputStream<Prof> create(E experiment, StreamOptions options, Optional<Collection<String>> keepGeneIds);

}
