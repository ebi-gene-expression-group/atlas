package uk.ac.ebi.atlas.profiles.baseline;

import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.model.ExpressionUnit;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExpression;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineProfile;
import uk.ac.ebi.atlas.model.resource.AtlasResource;
import uk.ac.ebi.atlas.model.resource.KryoFile;
import uk.ac.ebi.atlas.profiles.BaselineExpressionsKryoReader;
import uk.ac.ebi.atlas.profiles.BaselineProfileKryoInputStream;
import uk.ac.ebi.atlas.profiles.tsv.ExpressionsRowDeserializer;
import uk.ac.ebi.atlas.profiles.tsv.ExpressionsRowDeserializerBuilder;
import uk.ac.ebi.atlas.resource.DataFileHub;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class RnaSeqBaselineProfileStreamFactory extends BaselineProfileStreamFactory<BaselineProfileStreamOptions<ExpressionUnit.Absolute.Rna>> {

    @Inject
    RnaSeqBaselineProfileStreamFactory(DataFileHub dataFileHub) {
        super(dataFileHub);
    }

    @Override
    protected ExpressionsRowDeserializerBuilder<BaselineExpression>
    getExpressionsRowDeserializerBuilder(BaselineExperiment experiment) {
        return new RnaSeqBaselineExpressionsRowDeserializerBuilder(experiment);
    }

    @Override
    public ObjectInputStream<BaselineProfile> create(BaselineExperiment experiment,
                                                     BaselineProfileStreamOptions<ExpressionUnit.Absolute.Rna> baselineProfileStreamOptions) {
        /*
        TODO get the right file from DataFileHub
         */
        @SuppressWarnings("unused")
        ExpressionUnit.Absolute.Rna unit = baselineProfileStreamOptions.getExpressionUnit();
        AtlasResource<KryoFile.Handle> kryoFile = dataFileHub.getKryoFile(experiment.getAccession());
        if (kryoFile.exists()) {
            return new BaselineProfileKryoInputStream(
                    BaselineExpressionsKryoReader.create(kryoFile), experiment,
                    filterExpressions(experiment, baselineProfileStreamOptions));
        } else {
            return super.create(experiment, baselineProfileStreamOptions);
        }
    }


    static class RnaSeqBaselineExpressionsRowDeserializerBuilder
            implements ExpressionsRowDeserializerBuilder<BaselineExpression> {

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
