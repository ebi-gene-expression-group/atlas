package uk.ac.ebi.atlas.profiles.baseline;

import com.esotericsoftware.kryo.io.UnsafeInput;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExpression;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineProfile;
import uk.ac.ebi.atlas.model.resource.AtlasResource;
import uk.ac.ebi.atlas.profiles.BaselineExpressionsKryoReader;
import uk.ac.ebi.atlas.profiles.BaselineProfileKryoInputStream;
import uk.ac.ebi.atlas.profiles.tsv.ExpressionsRowDeserializer;
import uk.ac.ebi.atlas.profiles.tsv.ExpressionsRowDeserializerBuilder;
import uk.ac.ebi.atlas.resource.DataFileHub;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class RnaSeqBaselineProfileStreamFactory extends BaselineProfileStreamFactory {

    @Inject
    RnaSeqBaselineProfileStreamFactory(DataFileHub dataFileHub) {
        super(dataFileHub);
    }

    @Override
    protected ExpressionsRowDeserializerBuilder<BaselineExpression> getExpressionsRowDeserializerBuilder(BaselineExperiment experiment) {
        return new RnaSeqBaselineExpressionsRowDeserializerBuilder(experiment);
    }

    @Override
    public ObjectInputStream<BaselineProfile> create(BaselineExperiment experiment, BaselineProfileStreamOptions
            baselineProfileStreamOptions){
        AtlasResource<UnsafeInput> kryoFile = dataFileHub.getKryoFileForReading(experiment.getAccession());
        if(kryoFile.exists()){
            return new BaselineProfileKryoInputStream(
                    BaselineExpressionsKryoReader.create(kryoFile), experiment, filterExpressions(experiment,
                    baselineProfileStreamOptions));
        } else {
            return super.create(experiment, baselineProfileStreamOptions);
        }
    }


    static class RnaSeqBaselineExpressionsRowDeserializerBuilder implements ExpressionsRowDeserializerBuilder<BaselineExpression> {

        protected final BaselineExperiment baselineExperiment;

        public RnaSeqBaselineExpressionsRowDeserializerBuilder(BaselineExperiment baselineExperiment) {
            this.baselineExperiment = baselineExperiment;
        }


        @Override
        public ExpressionsRowDeserializer<BaselineExpression> build(String... tsvFileHeaders) {

            return new ExpressionsRowTsvDeserializerBaseline(baselineExperiment.getDataColumnDescriptors());
        }

    }
}
