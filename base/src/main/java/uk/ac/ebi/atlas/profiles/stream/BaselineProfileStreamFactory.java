package uk.ac.ebi.atlas.profiles.stream;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExpression;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineProfile;
import uk.ac.ebi.atlas.model.experiment.baseline.QuartilesArrayBuilder;
import uk.ac.ebi.atlas.profiles.baseline.BaselineProfileStreamOptions;
import uk.ac.ebi.atlas.profiles.baseline.IsBaselineExpressionAboveCutoffAndForFilterFactors;
import uk.ac.ebi.atlas.resource.DataFileHub;

import javax.annotation.Nullable;
import java.util.Map;

public abstract class BaselineProfileStreamFactory<StreamOptions extends BaselineProfileStreamOptions<?>> extends CreatesProfilesFromTsvFiles<AssayGroup, BaselineExpression,
        BaselineExperiment, StreamOptions,BaselineProfile> {

    BaselineProfileStreamFactory(DataFileHub dataFileHub) {
        super(dataFileHub);
    }

    @Override
    protected Function<String[], ProfileFromTsvLine> howToReadLineStream(final BaselineExperiment experiment, final Predicate<BaselineExpression> expressionFilter) {
        return new Function<String[], ProfileFromTsvLine>() {
            @Override
            public ProfileFromTsvLine apply(String[] strings) {
                return new ProfileFromTsvLine(rowPositionsToDataColumns(experiment, strings), expressionFilter) {
                    @Nullable
                    @Override
                    protected BaselineExpression nextExpression(Integer index,AssayGroup assayGroup, String[] currentLine) {

                        return createExpression(currentLine[index], assayGroup);
                    }

                    @Override
                    protected BaselineProfile newProfile(String[] currentLine) {
                        return new BaselineProfile(currentLine[0], currentLine[1]);
                    }
                };
            }
        };
    }

    private static final String ZERO_CODE = "-";
    private static final double[] ZERO_QUARTILES = new double[]{0.0, 0.0, 0.0, 0.0, 0.0};

    static BaselineExpression createExpression(String expressionLevelString, AssayGroup assayGroup) {

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
    protected Predicate<BaselineExpression> filterExpressions(BaselineExperiment experiment, StreamOptions options) {
        IsBaselineExpressionAboveCutoffAndForFilterFactors baselineExpressionFilter = new IsBaselineExpressionAboveCutoffAndForFilterFactors();
        baselineExpressionFilter.setCutoff(options.getCutoff());
        //TODO pay attention to other options
        // we used to only pick up expressions that will later be retrieved
        // e.g.
        // baselineExpressionFilter.setFilterFactors(filterFactors);
        return baselineExpressionFilter;
    }

    protected abstract Map<Integer, AssayGroup> rowPositionsToDataColumns(BaselineExperiment experiment, String[] headers);

}
