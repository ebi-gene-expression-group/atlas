package uk.ac.ebi.atlas.profiles.stream;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import org.apache.commons.lang3.tuple.Pair;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.experimentpage.context.RnaSeqRequestContext;
import uk.ac.ebi.atlas.model.DescribesDataColumns;
import uk.ac.ebi.atlas.model.Expression;
import uk.ac.ebi.atlas.model.Profile;
import uk.ac.ebi.atlas.model.experiment.Experiment;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.profiles.differential.ProfileStreamOptions;
import uk.ac.ebi.atlas.profiles.tsv.ExpressionsRowDeserializerBuilder;
import uk.ac.ebi.atlas.profiles.tsv.TsvInputStream;
import uk.ac.ebi.atlas.resource.DataFileHub;

import java.io.Reader;

public abstract class CreatesProfilesFromTsvFiles<DataColumnDescriptor extends DescribesDataColumns, Expr extends Expression,
        E extends Experiment<DataColumnDescriptor>, StreamOptions extends ProfileStreamOptions<DataColumnDescriptor>,
        Prof extends Profile<DataColumnDescriptor, Expr, Prof>> extends ProfileStreamFactory<DataColumnDescriptor, Expr, E, StreamOptions, Prof> {

    protected final DataFileHub dataFileHub;

    protected CreatesProfilesFromTsvFiles(DataFileHub dataFileHub) {
        this.dataFileHub = dataFileHub;
    }

    protected abstract Predicate<Expr> filterExpressions(E experiment, StreamOptions options);

    protected abstract ExpressionsRowDeserializerBuilder<Expr> getExpressionsRowDeserializerBuilder(E experiment);

    protected abstract ObjectInputStream<String[]> getDataFileReader(E experiment, StreamOptions options);

    //overriden for microarray
    protected Pair<String[], ObjectInputStream<String[]>> getHeadersAndBodyOfTsvFile(E experiment, StreamOptions options){
        ObjectInputStream<String[]> o = getDataFileReader(experiment, options);
        return Pair.of(o.readNext(), o);
    }

    //overriden for microarray
    protected Integer identifierColumns(){
        return 2;
    }

    protected abstract Function<String[], Prof> createProfileFromIdentifiers();

    ObjectInputStream<Prof> create(E experiment, StreamOptions options, Predicate<Expr> filterExpressions){
        return new TsvInputStream<>(getHeadersAndBodyOfTsvFile(experiment, options), getExpressionsRowDeserializerBuilder(experiment), filterExpressions, experiment, identifierColumns(), createProfileFromIdentifiers());
    }

    public final ObjectInputStream<Prof> create(E experiment, StreamOptions options){
        return create(experiment, options, filterExpressions(experiment, options));
    }

}
