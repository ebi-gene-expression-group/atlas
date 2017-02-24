package uk.ac.ebi.atlas.profiles;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.model.DescribesDataColumns;
import uk.ac.ebi.atlas.model.Expression;
import uk.ac.ebi.atlas.model.GeneProfilesList;
import uk.ac.ebi.atlas.model.Profile;
import uk.ac.ebi.atlas.model.experiment.Experiment;
import uk.ac.ebi.atlas.profiles.differential.ProfileStreamOptions;
import uk.ac.ebi.atlas.profiles.tsv.ExpressionsRowDeserializerBuilder;
import uk.ac.ebi.atlas.profiles.writer.ProfileWriter;
import uk.ac.ebi.atlas.resource.DataFileHub;

public abstract class ProfileStreamFactory<DataColumnDescriptor extends DescribesDataColumns, Expr extends Expression,
        E extends Experiment<DataColumnDescriptor>, StreamOptions extends ProfileStreamOptions<DataColumnDescriptor>,
        Prof extends Profile<DataColumnDescriptor, Expr>> {

    protected final DataFileHub dataFileHub;

    protected ProfileStreamFactory(DataFileHub dataFileHub){
        this.dataFileHub = dataFileHub;
    }

    protected abstract Predicate<Expr> filterExpressions(E experiment, StreamOptions options);

    protected abstract ExpressionsRowDeserializerBuilder<Expr> getExpressionsRowDeserializerBuilder(E experiment);

    protected abstract ObjectInputStream<Prof> create(E experiment, StreamOptions options);

    private Iterable<Prof> getProfiles(E experiment, StreamOptions streamOptions, Function<Iterable<Prof>,
            Iterable<Prof>>
            transform){
        return transform.apply(new IterableObjectInputStream<>(create(experiment,streamOptions)));
    }

    public <L extends GeneProfilesList<Prof> > L select(E experiment, StreamOptions streamOptions, Function<Iterable<Prof>,
            Iterable<Prof>> transform, SelectProfiles<Prof, L> selectProfiles){
        return selectProfiles.select(getProfiles(experiment, streamOptions, transform),
                streamOptions.getHeatmapMatrixSize());
    }

    public long write(E experiment, StreamOptions streamOptions, Function<Iterable<Prof>,
            Iterable<Prof>> transform, ProfileWriter<Prof> profileWriter){
        return profileWriter.write(getProfiles(experiment, streamOptions, transform));
    }

}
