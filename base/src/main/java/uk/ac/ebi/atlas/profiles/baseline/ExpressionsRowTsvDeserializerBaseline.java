package uk.ac.ebi.atlas.profiles.baseline;

import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExpression;
import uk.ac.ebi.atlas.model.experiment.baseline.QuartilesArrayBuilder;
import uk.ac.ebi.atlas.profiles.tsv.ExpressionsRowDeserializer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class ExpressionsRowTsvDeserializerBaseline implements ExpressionsRowDeserializer<BaselineExpression> {

    private static final String ZERO_CODE = "-";
    private static final double[] ZERO_QUARTILES = new double[]{0.0, 0.0, 0.0, 0.0, 0.0};


    private final Map<Integer, AssayGroup> dataColumnsForRowPositions;

    // baseline expression files have at most one column per row that the webapp considers relevant
    public ExpressionsRowTsvDeserializerBaseline(Map<Integer, AssayGroup> dataColumnsForRowPositions){
        this.dataColumnsForRowPositions = dataColumnsForRowPositions;
    }

    private BaselineExpression createExpression(String expressionLevelString, AssayGroup assayGroup) {

        if (expressionLevelString == null) {
            return null;
        }

        if (expressionLevelString.equals(ZERO_CODE)) {
            return new BaselineExpression(ZERO_QUARTILES, assayGroup.getId());
        }

        if (expressionLevelString.contains(",")) {
            return new BaselineExpression(QuartilesArrayBuilder.create(expressionLevelString), assayGroup.getId());
        }

        return new BaselineExpression(expressionLevelString, assayGroup.getId());
    }



    @Override
    public Collection<BaselineExpression> deserializeRow(String[] values) {
        List<BaselineExpression> result = new ArrayList<>();
        for(int column = 0; column< values.length ; column++){
            if(dataColumnsForRowPositions.containsKey(new Integer(column))){
                result.add(createExpression(values[column], dataColumnsForRowPositions.get(new Integer(column))));
            }
        }

        return result;
    }
}
