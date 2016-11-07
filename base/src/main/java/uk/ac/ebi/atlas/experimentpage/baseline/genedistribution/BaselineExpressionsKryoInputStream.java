package uk.ac.ebi.atlas.experimentpage.baseline.genedistribution;

import uk.ac.ebi.atlas.model.baseline.BaselineExpression;
import uk.ac.ebi.atlas.profiles.baseline.ExpressionsRowRawDeserializerBaselineBuilder;
import uk.ac.ebi.atlas.profiles.BaselineExpressionsKryoReader;
import uk.ac.ebi.atlas.profiles.KryoInputStream;

public class BaselineExpressionsKryoInputStream extends KryoInputStream<BaselineExpressions, BaselineExpression> {

    public BaselineExpressionsKryoInputStream(BaselineExpressionsKryoReader baselineExpressionsKryoReader, String experimentAccession, ExpressionsRowRawDeserializerBaselineBuilder baselineExpressionsQueueBuilder) {
        super(baselineExpressionsKryoReader, experimentAccession, baselineExpressionsQueueBuilder);
    }

    @Override
    protected BaselineExpressions buildObjectFromValues(String geneId, String geneName, BaselineExpression[] expressions) {

        BaselineExpressions baselineExpressions = new BaselineExpressions();
        for (BaselineExpression expression : expressions) {
            baselineExpressions.addExpression(expression);
        }

        return baselineExpressions;
    }

    @Override
    public BaselineExpressions createProfile() {
        return null;
    }

    @Override
    public void addExpressionToBuilder(BaselineExpression expression) {
    }

    @Override
    public void addGeneInfoValueToBuilder(String[] values) {
    }

}
