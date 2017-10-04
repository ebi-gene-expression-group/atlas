package uk.ac.ebi.atlas.profiles.stream;

import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExpression;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineProfile;
import uk.ac.ebi.atlas.profiles.baseline.BaselineProfileStreamOptions;
import uk.ac.ebi.atlas.profiles.baseline.IsBaselineExpressionAboveCutoff;
import uk.ac.ebi.atlas.resource.DataFileHub;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

public abstract class BaselineProfileStreamFactory<StreamOptions extends BaselineProfileStreamOptions<?>> extends
        CreatesProfilesFromTsvFiles<
                AssayGroup, BaselineExpression, BaselineExperiment, StreamOptions, BaselineProfile> {

    BaselineProfileStreamFactory(DataFileHub dataFileHub) {
        super(dataFileHub);
    }

    @Override
    protected Function<String[], Function<String[], BaselineProfile>> howToReadLine(
            final BaselineExperiment experiment, final Predicate<BaselineExpression> expressionFilter) {

        return strings ->
                new GoThroughTsvLineAndPickUpExpressionsByIndex(
                        rowPositionsToDataColumns(experiment, strings),
                        expressionFilter) {
            @Nullable
            @Override
            protected BaselineExpression nextExpression(Integer index, AssayGroup assayGroup, String[] currentLine) {
                return BaselineExpression.create(currentLine[index]);
            }

            @Override
            protected BaselineProfile newProfile(String[] currentLine) {
                return new BaselineProfile(currentLine[0], currentLine[1]);
            }
        };

    }

    @Override
    protected Predicate<BaselineExpression> filterExpressions(BaselineExperiment experiment, StreamOptions options) {
        IsBaselineExpressionAboveCutoff baselineExpressionFilter = new IsBaselineExpressionAboveCutoff();
        baselineExpressionFilter.setCutoff(options.getCutoff());
        //TODO pay attention to other options
        // we used to only pick up expressions that will later be retrieved
        // e.g.
        // baselineExpressionFilter.setFilterFactors(filterFactors);
        return baselineExpressionFilter;
    }

    protected abstract Map<Integer, AssayGroup> rowPositionsToDataColumns(BaselineExperiment experiment,
                                                                          String[] headers);

}
