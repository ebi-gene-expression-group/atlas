package uk.ac.ebi.atlas.profiles;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.base.Predicate;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.model.DescribesDataColumns;
import uk.ac.ebi.atlas.model.Expression;
import uk.ac.ebi.atlas.model.GeneProfilesList;
import uk.ac.ebi.atlas.model.Profile;
import uk.ac.ebi.atlas.model.experiment.Experiment;
import uk.ac.ebi.atlas.profiles.differential.ProfileStreamOptions;
import uk.ac.ebi.atlas.profiles.tsv.ExpressionsRowDeserializerBuilder;
import uk.ac.ebi.atlas.profiles.writer.ProfilesWriter;
import uk.ac.ebi.atlas.resource.DataFileHub;

import java.io.Reader;

public abstract class ProfileStreamFactory<DataColumnDescriptor extends DescribesDataColumns, Expr extends Expression,
        E extends Experiment<DataColumnDescriptor>, StreamOptions extends ProfileStreamOptions<DataColumnDescriptor>,
        Prof extends Profile<DataColumnDescriptor, Expr>> {

    protected final DataFileHub dataFileHub;

    protected ProfileStreamFactory(DataFileHub dataFileHub) {
        this.dataFileHub = dataFileHub;
    }

    protected abstract Predicate<Expr> filterExpressions(E experiment, StreamOptions options);

    protected abstract ExpressionsRowDeserializerBuilder<Expr> getExpressionsRowDeserializerBuilder(E experiment);

    public abstract ObjectInputStream<Prof> create(E experiment, StreamOptions options);

    private Iterable<Prof> getProfiles(E experiment, StreamOptions streamOptions, Function<Iterable<Prof>,
            Iterable<Prof>>
            transform) {
        return transform.apply(new IterableObjectInputStream<>(create(experiment, streamOptions)));
    }

    public <L extends GeneProfilesList<Prof>> L select(E experiment, StreamOptions streamOptions, Function<Iterable<Prof>,
            Iterable<Prof>> transform, SelectProfiles<Prof, L> selectProfiles) {
        return selectProfiles.select(getProfiles(experiment, streamOptions, transform),
                streamOptions.getHeatmapMatrixSize());
    }

    public long write(E experiment, StreamOptions streamOptions, Function<Iterable<Prof>,
            Iterable<Prof>> transform, ProfilesWriter<Prof> profilesWriter) {
        return profilesWriter.write(getProfiles(experiment, streamOptions, transform));
    }

    public int[] histogram(E experiment, StreamOptions streamOptions, double[] cutoffBins) {
        int[] result = new int[cutoffBins.length];

        for (Prof prof : getProfiles(experiment, streamOptions, Functions.<Iterable<Prof>>identity())) {

            double expressionValue = prof.getMaxExpressionLevelOn(streamOptions.getDataColumnsToReturn());

            for (int i = cutoffBins.length - 1 ; i >= 0 ; i--) {
                if (expressionValue >= cutoffBins[i]) {
                    result[i] += 1;
                    break;
                }
            }
        }
        return result;
    }

}
