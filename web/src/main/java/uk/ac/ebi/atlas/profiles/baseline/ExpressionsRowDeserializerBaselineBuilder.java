
package uk.ac.ebi.atlas.profiles.baseline;

import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.baseline.BaselineExpression;
import uk.ac.ebi.atlas.profiles.ExpressionsRowDeserializerBuilder;
import uk.ac.ebi.atlas.trader.cache.BaselineExperimentsCache;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.concurrent.ExecutionException;

import static com.google.common.base.Preconditions.checkState;

@Named
@Scope("prototype")
public class ExpressionsRowDeserializerBaselineBuilder implements ExpressionsRowDeserializerBuilder<String, BaselineExpression> {

    private String experimentAccession;

    private BaselineExperimentsCache experimentsCache;

    public ExpressionsRowDeserializerBaselineBuilder() {
        //for subclassing
    }

    @Inject
    public ExpressionsRowDeserializerBaselineBuilder(BaselineExperimentsCache experimentsCache) {
        this.experimentsCache = experimentsCache;
    }

    @Override
    public ExpressionsRowDeserializerBaselineBuilder forExperiment(String experimentAccession) {
        this.experimentAccession = experimentAccession;
        return this;
    }

    @Override
    public ExpressionsRowDeserializerBaselineBuilder withHeaders(String... tsvFileHeaders) {
        //We don't need to process the headers for Baseline
        //we use orderedFactorGroups from BaselineExperiment instead
        return this;
    }

    @Override
    public ExpressionsRowTsvDeserializerBaseline build() {
        try {
            checkState(experimentAccession != null, "Please invoke forExperiment before invoking the build method");

            BaselineExperiment baselineExperiment = experimentsCache.getExperiment(experimentAccession);

            //TODO: ordered factor groups should be passed in from the top, not looked up here
            return new ExpressionsRowTsvDeserializerBaseline(baselineExperiment.getExperimentalFactors().getFactorGroupsInOrder());
        } catch (ExecutionException e) {
            throw new IllegalStateException("Failed to load experiment from cache: " + experimentAccession, e);
        }
    }

}