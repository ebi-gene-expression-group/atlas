package uk.ac.ebi.atlas.profiles.baseline;

import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.model.ExpressionUnit;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExpression;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineProfile;
import uk.ac.ebi.atlas.profiles.tsv.ExpressionsRowDeserializerBuilder;
import uk.ac.ebi.atlas.resource.DataFileHub;
import uk.ac.ebi.atlas.utils.StringArrayUtil;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;

@Named
public class ProteomicsBaselineProfileStreamFactory extends BaselineProfileStreamFactory<BaselineProfileStreamOptions<ExpressionUnit.Absolute.Protein>> {
    @Inject
    ProteomicsBaselineProfileStreamFactory(DataFileHub dataFileHub) {
        super(dataFileHub);
    }

    @Override
    protected ExpressionsRowDeserializerBuilder<BaselineExpression> getExpressionsRowDeserializerBuilder(BaselineExperiment experiment) {
        return new ProteomicsBaselineExpressionsRowDeserializerBuilder(experiment);
    }

    @Override
    public ObjectInputStream<BaselineProfile> create(BaselineExperiment experiment, BaselineProfileStreamOptions<ExpressionUnit.Absolute.Protein> options) {
        try {
            return create(experiment, options, dataFileHub.getProteomicsBaselineExperimentFiles(experiment.getAccession()).main.getReader(), new ProteomicsBaselineExpressionsRowDeserializerBuilder(experiment));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static class ProteomicsBaselineExpressionsRowDeserializerBuilder implements ExpressionsRowDeserializerBuilder<BaselineExpression> {

        private final BaselineExperiment baselineExperiment;
        public ProteomicsBaselineExpressionsRowDeserializerBuilder(BaselineExperiment baselineExperiment) {
            this.baselineExperiment = baselineExperiment;
        }

        @Override
        public ExpressionsRowTsvDeserializerBaseline build(String... tsvFileHeaders) {

            return new ExpressionsRowTsvDeserializerBaseline(baselineExperiment.getDataColumnDescriptors(), StringArrayUtil.indicesOf
                    (tsvFileHeaders, "WithInSampleAbundance"));
        }

    }
}
