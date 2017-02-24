package uk.ac.ebi.atlas.profiles.differential.microarray;

import com.google.common.base.Function;
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
import uk.ac.ebi.atlas.profiles.tsv.TsvInputStream;
import uk.ac.ebi.atlas.resource.DataFileHub;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.Vector;
@Named
public class MicroarrayProfileStreamFactory
extends DifferentialProfileStreamFactory<MicroarrayExpression, MicroarrayExperiment, MicroarrayRequestContext, MicroarrayProfile> {

    @Inject
    public MicroarrayProfileStreamFactory(DataFileHub dataFileHub) {
        super(dataFileHub);
        new Function<String[], MicroarrayProfile>() {
            @Nullable
            @Override
            public MicroarrayProfile apply(@Nullable String[] identifiers) {
                return new MicroarrayProfile(identifiers[0], identifiers[1], identifiers[2]);
            }
        };
    }

    @Override
    public ObjectInputStream<MicroarrayProfile> create(MicroarrayExperiment experiment, MicroarrayRequestContext
            options){
        Vector<ObjectInputStream<MicroarrayProfile>> inputStreams = new Vector<>();
        for (String arrayDesignAccession : options.getArrayDesignAccessions()) {
            ObjectInputStream<MicroarrayProfile> stream = create(experiment, options, arrayDesignAccession);

            inputStreams.add(stream);
        }

        return new SequenceObjectInputStream<>(inputStreams.elements());
    }

    public Reader openDataFile(String experimentAccession, String arrayDesignAccession){
        try {
            return dataFileHub.getMicroarrayExperimentFiles(experimentAccession, arrayDesignAccession).analytics.getReader();
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    public ObjectInputStream<MicroarrayProfile> create(MicroarrayExperiment experiment,
                                                   MicroarrayProfileStreamOptions options,
                                                   String arrayDesignAccession){

        return new TsvInputStream<>(openDataFile(experiment.getAccession(), arrayDesignAccession),
                getExpressionsRowDeserializerBuilder(experiment), filterExpressions(experiment, options), experiment, 3,
                new Function<String[], MicroarrayProfile>() {
                    @Nullable
                    @Override
                    public MicroarrayProfile apply(@Nullable String[] identifiers) {
                        return new MicroarrayProfile(identifiers[0], identifiers[1], identifiers[2]);
                    }
                });
    }

    @Override
    protected ExpressionsRowDeserializerBuilder<MicroarrayExpression> getExpressionsRowDeserializerBuilder(MicroarrayExperiment experiment) {
        return new MicroarrayExpressionsRowDeserializerBuilder(experiment);
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
