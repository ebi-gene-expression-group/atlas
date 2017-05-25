package uk.ac.ebi.atlas.profiles.stream;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import uk.ac.ebi.atlas.model.DescribesDataColumns;
import uk.ac.ebi.atlas.model.Expression;
import uk.ac.ebi.atlas.model.GeneProfilesList;
import uk.ac.ebi.atlas.model.Profile;
import uk.ac.ebi.atlas.model.experiment.Experiment;
import uk.ac.ebi.atlas.profiles.IterableObjectInputStream;
import uk.ac.ebi.atlas.profiles.SelectProfiles;
import uk.ac.ebi.atlas.profiles.differential.ProfileStreamOptions;
import uk.ac.ebi.atlas.profiles.writer.ProfilesWriter;

public abstract class ProfileStreamFactory<DataColumnDescriptor extends DescribesDataColumns, Expr extends Expression,
        E extends Experiment<DataColumnDescriptor>, StreamOptions extends ProfileStreamOptions<DataColumnDescriptor>,
        Prof extends Profile<DataColumnDescriptor, Expr, Prof>> implements CreatesProfileStream<DataColumnDescriptor, Expr, E, StreamOptions, Prof> {

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
