package uk.ac.ebi.atlas.profiles;

import com.google.common.base.Predicate;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.model.DescribesDataColumns;
import uk.ac.ebi.atlas.model.Expression;
import uk.ac.ebi.atlas.model.Profile;
import uk.ac.ebi.atlas.model.experiment.Experiment;
import uk.ac.ebi.atlas.profiles.differential.ProfileStreamOptions;
import uk.ac.ebi.atlas.profiles.tsv.ExpressionsRowDeserializerBuilder;
import uk.ac.ebi.atlas.resource.DataFileHub;

public abstract class ProfileStreamFactory<DataColumnDescriptor extends DescribesDataColumns, Expr extends Expression,
        E extends Experiment<DataColumnDescriptor>, T extends ProfileStreamOptions<DataColumnDescriptor>, Prof extends Profile<DataColumnDescriptor, Expr>> {

    protected final DataFileHub dataFileHub;

    protected ProfileStreamFactory(DataFileHub dataFileHub){
        this.dataFileHub = dataFileHub;
    }

    protected abstract Predicate<Expr> filterExpressions(E experiment, T options);

    protected abstract ExpressionsRowDeserializerBuilder<Expr> getExpressionsRowDeserializerBuilder(E experiment);

    public abstract ObjectInputStream<Prof> create(E experiment, T options);

}
