package uk.ac.ebi.atlas.profiles.stream;

import com.google.common.collect.ImmutableList;
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
import java.util.Collections;
import java.util.function.Predicate;

public abstract class ProfileStreamFactory<D extends DescribesDataColumns,
                                           E extends Expression,
                                           T extends Experiment<D>,
                                           O extends ProfileStreamOptions<D>,
                                           P extends Profile<D, E, P>>

implements CreatesProfileStream<D, E, T, O, P> {

    private ObjectInputStream<P> getProfiles(T experiment,
                                             O streamOptions,
                                             Collection<String> keepGeneIds,
                                             Predicate<P> keepProfiles) {
        return ObjectInputStreams.filter(create(experiment, streamOptions, keepGeneIds), keepProfiles);
    }

    public <L extends GeneProfilesList<P>> L select(T experiment,
                                                    O streamOptions,
                                                    Collection<String> keepGeneIds,
                                                    Predicate<P> keepProfiles,
                                                    SelectProfiles<P, L> selectProfiles) {
        return selectProfiles.select(getProfiles(experiment, streamOptions, keepGeneIds, keepProfiles),
                streamOptions.getHeatmapMatrixSize());
    }

    public GeneProfilesList<P> getAllMatchingProfiles(T experiment,
                                                      O streamOptions,
                                                      Collection<String> keepGeneIds){
        return new GeneProfilesList<>(ImmutableList.copyOf(
                new IterableObjectInputStream<>(getProfiles(experiment,
                                                            streamOptions,
                                                            keepGeneIds,
                                                            profile -> true))));
    }

    public long write(T experiment,
                      O streamOptions,
                      Collection<String> keepGeneIds,
                      Predicate<P> keepProfiles,
                      ProfilesWriter<P> profilesWriter) {
        return profilesWriter.write(getProfiles(experiment, streamOptions,keepGeneIds, keepProfiles));
    }

    public int[] histogram(T experiment, O streamOptions, double[] cutoffBins) {
        int[] result = new int[cutoffBins.length];

        for (P prof : new IterableObjectInputStream<>(
                getProfiles(experiment, streamOptions, Collections.emptySet(), x -> true))) {

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
