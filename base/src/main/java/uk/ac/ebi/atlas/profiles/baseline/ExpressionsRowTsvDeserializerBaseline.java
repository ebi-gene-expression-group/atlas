package uk.ac.ebi.atlas.profiles.baseline;

import com.google.common.base.Joiner;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExpression;
import uk.ac.ebi.atlas.model.experiment.baseline.QuartilesArrayBuilder;
import uk.ac.ebi.atlas.profiles.tsv.ExpressionsRowDeserializer;
import uk.ac.ebi.atlas.utils.StringArrayUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class ExpressionsRowTsvDeserializerBaseline implements ExpressionsRowDeserializer<BaselineExpression> {

    private static final String ZERO_CODE = "-";
    private static final double[] ZERO_QUARTILES = new double[]{0.0, 0.0, 0.0, 0.0, 0.0};

    private final List<AssayGroup> assayGroups;
    private final int[] indicesForDataPoints;

    public ExpressionsRowTsvDeserializerBaseline(List<AssayGroup> assayGroups) {
        this.assayGroups = assayGroups;
        this.indicesForDataPoints = new int[assayGroups.size()];
        for(int i = 0 ; i< indicesForDataPoints.length ; i ++){
            indicesForDataPoints[i]=i;
        }
    }
    // baseline expression files have at most one column per row that the webapp considers relevant
    public ExpressionsRowTsvDeserializerBaseline(List<AssayGroup> assayGroups, int[] indicesForDataPoints) {
        if (assayGroups.size() != indicesForDataPoints.length) {
            throw new IllegalArgumentException(String.format("There are %s assay groups in the experiment, but I am " +
                    "expected to look for %s data points!",
                    assayGroups.size(),
                    indicesForDataPoints.length));
        }
        this.assayGroups = assayGroups;
        this.indicesForDataPoints = indicesForDataPoints;
    }

    private BaselineExpression nextExpression(String expressionLevelString, String assayGroupId) {

        if (expressionLevelString == null) {
            return null;
        }

        if (expressionLevelString.equals(ZERO_CODE)) {
            return new BaselineExpression(ZERO_QUARTILES, assayGroupId);
        }

        if (expressionLevelString.contains(",")) {
            return new BaselineExpression(QuartilesArrayBuilder.create(expressionLevelString), assayGroupId);
        }

        return new BaselineExpression(expressionLevelString, assayGroupId);
    }


    @Override
    public Collection<BaselineExpression> deserializeRow(String[] values) {
        String[] filtered = StringArrayUtil.filterByIndices(values, indicesForDataPoints);
        if (filtered.length != assayGroups.size()) {
            throw new IllegalArgumentException(String.format("Expected %s values in positions %s but got [%s]",
                    assayGroups.size(),
                    Arrays.toString(indicesForDataPoints),
                    Joiner.on(",").join(values)));
        }

        List<BaselineExpression> result = new ArrayList<>();

        for(int i = 0; i< filtered.length ; i++){
            result.add(nextExpression(filtered[i], assayGroups.get(i).getId()));
        }

        return result;
    }
}
