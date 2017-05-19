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
import java.io.IOException;

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
        AtlasResource<KryoFile.Handle> kryoFile = dataFileHub.getKryoFile(experiment.getAccession(), baselineProfileStreamOptions.getExpressionUnit());
        if (kryoFile.exists()) {
            return new BaselineProfileKryoInputStream(
                    BaselineExpressionsKryoReader.create(kryoFile), experiment,
                    filterExpressions(experiment, baselineProfileStreamOptions));
        } else {
            return createFromTsv(experiment, baselineProfileStreamOptions);
        }
    }

    public ObjectInputStream<BaselineProfile> createFromTsv(BaselineExperiment experiment, BaselineProfileStreamOptions<ExpressionUnit.Absolute.Rna> options) {
        try {
            return create(experiment, options, dataFileHub.getRnaSeqBaselineExperimentFiles(experiment.getAccession()).dataFile(options.getExpressionUnit()).getReader(), new RnaSeqBaselineExpressionsRowDeserializerBuilder(experiment));
        } catch (IOException e) {
            throw new RuntimeException(e);
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
