
package uk.ac.ebi.atlas.profiles.baseline;

import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.baseline.BaselineExpression;
import uk.ac.ebi.atlas.profiles.ExpressionsRowDeserializerBuilder;
import uk.ac.ebi.atlas.trader.cache.RnaSeqBaselineExperimentsCache;

import javax.inject.Inject;
import javax.inject.Named;

import static com.google.common.base.Preconditions.checkState;

@Named
@Scope("prototype")
public class ExpressionsRowRawDeserializerBaselineBuilder implements ExpressionsRowDeserializerBuilder<BaselineExpression, BaselineExpression> {

    private String experimentAccession;

    private RnaSeqBaselineExperimentsCache experimentsCache;

    @Inject
    public ExpressionsRowRawDeserializerBaselineBuilder(RnaSeqBaselineExperimentsCache experimentsCache) {
        this.experimentsCache = experimentsCache;
    }

    @Override
    public ExpressionsRowRawDeserializerBaselineBuilder forExperiment(String experimentAccession) {
        this.experimentAccession = experimentAccession;
        return this;
    }

    @Override
    public ExpressionsRowRawDeserializerBaselineBuilder withHeaders(String... tsvFileHeaders) {
        //We don't need to process the headers for Baseline
        //we use orderedFactorGroups from BaselineExperiment instead
        return this;
    }

    @Override
    public ExpressionsRowRawDeserializerBaseline build() {
        checkState(experimentAccession != null, "Please invoke forExperiment before invoking the build method");

        BaselineExperiment baselineExperiment = experimentsCache.getExperiment(experimentAccession);

        //TODO: ordered factor groups should be passed in from the top, not looked up here
        return new ExpressionsRowRawDeserializerBaseline(baselineExperiment.getExperimentalFactors().getFactorGroupsInOrder());

    }

}