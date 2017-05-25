package uk.ac.ebi.atlas.profiles.differential.microarray;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.lang3.tuple.Pair;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.commons.streams.SequenceObjectInputStream;
import uk.ac.ebi.atlas.experimentpage.context.MicroarrayRequestContext;
import uk.ac.ebi.atlas.model.experiment.differential.Contrast;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayExpression;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayProfile;
import uk.ac.ebi.atlas.profiles.differential.DifferentialProfileStreamFactory;
import uk.ac.ebi.atlas.profiles.tsv.ExpressionsRowDeserializer;
import uk.ac.ebi.atlas.profiles.tsv.ExpressionsRowDeserializerBuilder;
import uk.ac.ebi.atlas.profiles.tsv.MicroarrayExpressionsRowDeserializer;
import uk.ac.ebi.atlas.resource.DataFileHub;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;
@Named
public class MicroarrayProfileStreamFactory
extends DifferentialProfileStreamFactory<MicroarrayExpression, MicroarrayExperiment, MicroarrayRequestContext, MicroarrayProfile> {

    @Inject
    public MicroarrayProfileStreamFactory(DataFileHub dataFileHub) {
        super(dataFileHub);
    }

    @Override
    protected ExpressionsRowDeserializerBuilder<MicroarrayExpression> getExpressionsRowDeserializerBuilder(MicroarrayExperiment experiment) {
        return new MicroarrayExpressionsRowDeserializerBuilder(experiment);
    }

    @Override
    protected ObjectInputStream<String[]> getDataFileReader(MicroarrayExperiment experiment, MicroarrayRequestContext options) {
        throw new NotImplementedException("Should not use this");
    }

    /*
    TODO this is not right.
    There are multiple files so we have to concatenate them!

    From header you create whatToDoWithTheHeaders as before.
     */
    @Override
    protected Pair<String[], ObjectInputStream<String[]>> getHeadersAndBodyOfTsvFile(MicroarrayExperiment experiment, MicroarrayRequestContext options) {
        String[] header = null;
        Vector<ObjectInputStream<String[]>> inputStreams = new Vector<>();
        for (String arrayDesignAccession : options.getArrayDesignAccessions()) {
            ObjectInputStream<String[]> stream = dataFileHub.getMicroarrayExperimentFiles(experiment.getAccession(), arrayDesignAccession).analytics.get();
            String[] thisFileHeader = stream.readNext();
            Preconditions.checkState(header == null || Arrays.deepEquals(header, thisFileHeader) , "Inconsistent headers between different array designs :(");

            inputStreams.add(stream);
        }

        return Pair.<String[], ObjectInputStream<String[]>>of(header, new SequenceObjectInputStream<>(inputStreams.elements()));
    }

    @Override
    protected final Integer identifierColumns(){
        return 3;
    }

    @Override
    protected Function<String[], MicroarrayProfile> createProfileFromIdentifiers() {
        return new Function<String[], MicroarrayProfile>() {
            @Nullable
            @Override
            public MicroarrayProfile apply(@Nullable String[] identifiers) {
                return new MicroarrayProfile(identifiers[0], identifiers[1], identifiers[2]);
            }
        };
    }

    static class MicroarrayExpressionsRowDeserializerBuilder extends DifferentialProfileStreamFactory
            .DifferentialExpressionsRowDeserializerBuilder<MicroarrayExpression> {


        public MicroarrayExpressionsRowDeserializerBuilder(MicroarrayExperiment experiment) {
            super(experiment);
        }

        @Override
        protected ExpressionsRowDeserializer<MicroarrayExpression> getBufferInstance(List<Contrast> orderedContrasts) {
            return new MicroarrayExpressionsRowDeserializer(orderedContrasts);
        }

    }

}
