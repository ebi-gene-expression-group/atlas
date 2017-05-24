package uk.ac.ebi.atlas.profiles.differential.rnaseq;

import com.google.common.base.Function;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.experimentpage.context.RnaSeqRequestContext;
import uk.ac.ebi.atlas.model.experiment.differential.Contrast;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExpression;
import uk.ac.ebi.atlas.model.experiment.differential.rnaseq.RnaSeqProfile;
import uk.ac.ebi.atlas.profiles.differential.DifferentialProfileStreamFactory;
import uk.ac.ebi.atlas.profiles.tsv.ExpressionsRowDeserializer;
import uk.ac.ebi.atlas.profiles.tsv.ExpressionsRowDeserializerBuilder;
import uk.ac.ebi.atlas.profiles.tsv.RnaSeqDifferentialExpressionsRowDeserializer;
import uk.ac.ebi.atlas.profiles.tsv.TsvInputStream;
import uk.ac.ebi.atlas.resource.DataFileHub;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

@Named
public class RnaSeqProfileStreamFactory extends DifferentialProfileStreamFactory<DifferentialExpression,
        DifferentialExperiment, RnaSeqRequestContext, RnaSeqProfile> {

    @Inject
    protected RnaSeqProfileStreamFactory(DataFileHub dataFileHub) {
        super(dataFileHub);
    }

    @Override
    public ObjectInputStream<RnaSeqProfile> create(DifferentialExperiment experiment, RnaSeqRequestContext
            options) {

        return new TsvInputStream<>(getDataFileReader(experiment, options),
                getExpressionsRowDeserializerBuilder(experiment), filterExpressions(experiment, options), experiment, 2,
                new Function<String[], RnaSeqProfile>() {
                    @Nullable
                    @Override
                    public RnaSeqProfile apply(@Nullable String[] identifiers) {
                        return new RnaSeqProfile(identifiers[0], identifiers[1]);
                    }
                });
    }

    @Override
    protected ExpressionsRowDeserializerBuilder<DifferentialExpression> getExpressionsRowDeserializerBuilder(DifferentialExperiment experiment) {
        return new RnaSeqDifferentialExpressionsRowDeserializerBuilder(experiment);
    }

    //similar to BaslineProfileStreamFactory but can't extract a common interface because of microarray profiles
    protected Reader getDataFileReader(DifferentialExperiment experiment, RnaSeqRequestContext options) {
        try {
            return dataFileHub.getDifferentialExperimentFiles(experiment.getAccession()).analytics.getReader();
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    static class RnaSeqDifferentialExpressionsRowDeserializerBuilder extends DifferentialProfileStreamFactory
            .DifferentialExpressionsRowDeserializerBuilder<DifferentialExpression> {

        public RnaSeqDifferentialExpressionsRowDeserializerBuilder(DifferentialExperiment experiment) {
            super(experiment);
        }

        @Override
        protected ExpressionsRowDeserializer<DifferentialExpression> getBufferInstance(List<Contrast> orderedContrasts) {
            return new RnaSeqDifferentialExpressionsRowDeserializer(orderedContrasts);
        }

    }

}
