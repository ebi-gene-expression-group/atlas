package uk.ac.ebi.atlas.profiles.stream;

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

import java.util.Collection;
import java.util.Optional;
import java.util.function.Predicate;

public abstract class ProfileStreamFactory<DataColumnDescriptor extends DescribesDataColumns,
                                           Expr extends Expression,
                                           E extends Experiment<DataColumnDescriptor>,
                                           StreamOptions extends ProfileStreamOptions<DataColumnDescriptor>,
                                           Prof extends Profile<DataColumnDescriptor, Expr, Prof>>

implements CreatesProfileStream<DataColumnDescriptor, Expr, E, StreamOptions, Prof> {

    private ObjectInputStream<Prof> getProfiles(E experiment,
                                                StreamOptions streamOptions,
                                                Optional<Collection<String>> keepGeneIds,
                                                Predicate<Prof> keepProfiles) {
        return ObjectInputStreams.filter(create(experiment, streamOptions, keepGeneIds), keepProfiles);
    }

    public <L extends GeneProfilesList<Prof>> L select(E experiment,
                                                       StreamOptions streamOptions,
                                                       Optional<Collection<String>> keepGeneIds, Predicate<Prof> keepProfiles,
                                                       SelectProfiles<Prof, L> selectProfiles) {
        return selectProfiles.select(getProfiles(experiment, streamOptions,keepGeneIds, keepProfiles),
                streamOptions.getHeatmapMatrixSize());
    }

    public long write(E experiment, StreamOptions streamOptions, Optional<Collection<String>> keepGeneIds, Predicate<Prof> keepProfiles,
                      ProfilesWriter<Prof> profilesWriter) {
        return profilesWriter.write(getProfiles(experiment, streamOptions,keepGeneIds, keepProfiles));
    }

    public int[] histogram(E experiment, StreamOptions streamOptions, double[] cutoffBins) {
        int[] result = new int[cutoffBins.length];

        for (Prof prof : new IterableObjectInputStream<>(getProfiles(experiment, streamOptions,Optional.empty(), x -> true))) {

            result[binarySearch0(
                    cutoffBins, prof.getMaxExpressionLevelOn(streamOptions.getDataColumnsToReturn()))] += 1;
        }

        return result;
    }

    //java.util.Arrays.binarySearch0 modified to stop when key is between buckets, removed the NaN cases
    private static int binarySearch0(double[] a , double key) {
        int low = 0;
        int high = a.length - 1;

        while (low < high) {
            int mid = (low + high) >>> 1;
            double midVal = a[mid];

            if (midVal < key){
                low = mid + 1;
            }
            else if (midVal > key) {
                high = mid;
            } else {
                return mid;
            }

        }
        return low;
    }
}
