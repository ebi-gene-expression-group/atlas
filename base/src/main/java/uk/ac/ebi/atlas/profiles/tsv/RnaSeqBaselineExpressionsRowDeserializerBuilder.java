package uk.ac.ebi.atlas.profiles.tsv;

import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExpression;
import uk.ac.ebi.atlas.profiles.baseline.ExpressionsRowTsvDeserializerBaseline;
import uk.ac.ebi.atlas.profiles.tsv.ExpressionsRowDeserializer;
import uk.ac.ebi.atlas.profiles.tsv.ExpressionsRowDeserializerBuilder;

public class RnaSeqBaselineExpressionsRowDeserializerBuilder implements ExpressionsRowDeserializerBuilder<BaselineExpression> {

    protected final BaselineExperiment baselineExperiment;

    public RnaSeqBaselineExpressionsRowDeserializerBuilder(BaselineExperiment baselineExperiment) {
        this.baselineExperiment = baselineExperiment;
    }


    @Override
    public ExpressionsRowDeserializer<BaselineExpression> build(String... tsvFileHeaders) {

        return new ExpressionsRowTsvDeserializerBaseline(baselineExperiment.getDataColumnDescriptors());
    }

}