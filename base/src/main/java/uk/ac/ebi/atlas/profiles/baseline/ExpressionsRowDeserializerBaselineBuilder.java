package uk.ac.ebi.atlas.profiles.baseline;

import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.baseline.BaselineExpression;
import uk.ac.ebi.atlas.profiles.ExpressionsRowDeserializerBuilder;

public class ExpressionsRowDeserializerBaselineBuilder implements ExpressionsRowDeserializerBuilder<String, BaselineExpression> {

    protected final BaselineExperiment baselineExperiment;

    public ExpressionsRowDeserializerBaselineBuilder(BaselineExperiment baselineExperiment) {
        this.baselineExperiment = baselineExperiment;
    }


    @Override
    public ExpressionsRowTsvDeserializerBaseline build(String... tsvFileHeaders) {

        return new ExpressionsRowTsvDeserializerBaseline(baselineExperiment.getExperimentalFactors().getFactorGroupsInOrder());
    }

}