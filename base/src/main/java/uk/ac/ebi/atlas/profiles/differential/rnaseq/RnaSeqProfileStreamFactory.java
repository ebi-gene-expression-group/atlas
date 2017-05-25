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
import uk.ac.ebi.atlas.resource.DataFileHub;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
public class RnaSeqProfileStreamFactory extends DifferentialProfileStreamFactory<DifferentialExpression,
        DifferentialExperiment, RnaSeqRequestContext, RnaSeqProfile> {

    @Inject
    public RnaSeqProfileStreamFactory(DataFileHub dataFileHub) {
        super(dataFileHub);
    }

    @Override
    protected ExpressionsRowDeserializerBuilder<DifferentialExpression> getExpressionsRowDeserializerBuilder(DifferentialExperiment experiment) {
        return new RnaSeqDifferentialExpressionsRowDeserializerBuilder(experiment);
    }


    @Override
    protected ObjectInputStream<String[]> getDataFileReader(DifferentialExperiment experiment, RnaSeqRequestContext options) {
        return dataFileHub.getRnaSeqDifferentialExperimentFiles(experiment.getAccession()).analytics.get();
    }

    @Override
    protected Function<String[], RnaSeqProfile> createProfileFromIdentifiers() {
        return new Function<String[], RnaSeqProfile>() {
            @Nullable
            @Override
            public RnaSeqProfile apply(@Nullable String[] identifiers) {
                return new RnaSeqProfile(identifiers[0], identifiers[1]);
            }
        };
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
