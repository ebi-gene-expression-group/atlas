
package uk.ac.ebi.atlas.profiles.baseline;

import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExpression;
import uk.ac.ebi.atlas.profiles.ExpressionsRowDeserializerBuilder;

public class ExpressionsRowRawDeserializerBaselineBuilder implements ExpressionsRowDeserializerBuilder<BaselineExpression, BaselineExpression> {

    private final BaselineExperiment baselineExperiment;

    public ExpressionsRowRawDeserializerBaselineBuilder(BaselineExperiment baselineExperiment){
        this.baselineExperiment = baselineExperiment;
    }


    @Override
    public ExpressionsRowRawDeserializerBaseline build(String... tsvFileHeaders) {
        //We don't need to process the headers for Baseline
        //we use orderedFactorGroups from BaselineExperiment instead

        return new ExpressionsRowRawDeserializerBaseline(baselineExperiment.getExperimentalFactors().getFactorGroupsInOrder());

    }

}