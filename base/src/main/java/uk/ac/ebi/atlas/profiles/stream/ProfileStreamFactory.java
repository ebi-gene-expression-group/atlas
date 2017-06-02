package uk.ac.ebi.atlas.profiles.stream;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStreams;
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

    private ObjectInputStream<Prof> getProfiles(E experiment, StreamOptions streamOptions, Predicate<Prof> keepProfiles) {
        return ObjectInputStreams.filter(create(experiment, streamOptions), keepProfiles);
    }

    public <L extends GeneProfilesList<Prof>> L select(E experiment, StreamOptions streamOptions, Predicate<Prof> keepProfiles,
                                                       SelectProfiles<Prof, L> selectProfiles) {
        return selectProfiles.select(getProfiles(experiment, streamOptions, keepProfiles),
                streamOptions.getHeatmapMatrixSize());
    }

    public long write(E experiment, StreamOptions streamOptions, Predicate<Prof> keepProfiles, ProfilesWriter<Prof> profilesWriter) {
        return profilesWriter.write(getProfiles(experiment, streamOptions, keepProfiles));
    }

    public int[] histogram(E experiment, StreamOptions streamOptions, double[] cutoffBins) {
        int[] result = new int[cutoffBins.length];

        for (Prof prof : new IterableObjectInputStream<>(getProfiles(experiment, streamOptions, Predicates.<Prof>alwaysTrue()))) {

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
