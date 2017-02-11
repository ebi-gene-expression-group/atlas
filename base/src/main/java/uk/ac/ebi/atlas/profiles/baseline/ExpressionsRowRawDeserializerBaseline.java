package uk.ac.ebi.atlas.profiles.baseline;

import com.google.common.base.Joiner;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExpression;
import uk.ac.ebi.atlas.model.experiment.baseline.FactorGroup;
import uk.ac.ebi.atlas.profiles.ExpressionsRowRawDeserializer;

import java.util.List;
import java.util.Queue;

public class ExpressionsRowRawDeserializerBaseline extends ExpressionsRowRawDeserializer<BaselineExpression> {

    private  int expectedNumberOfValues;

    public ExpressionsRowRawDeserializerBaseline(List<FactorGroup> orderedFactorGroups) {
        expectedNumberOfValues = orderedFactorGroups.size();
    }

    @Override
    public ExpressionsRowRawDeserializer<BaselineExpression> reload(BaselineExpression... values) {
        if (values.length != expectedNumberOfValues) {
            throw new IllegalArgumentException(String.format("Expected %s values but got [%s]", expectedNumberOfValues, Joiner.on(",").join(values)));
        }
        return super.reload(values);
    }

    @Override
    public BaselineExpression nextExpression(Queue<BaselineExpression> rawValuesRow) {
        return rawValuesRow.poll();
    }

}
