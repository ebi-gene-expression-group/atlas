package uk.ac.ebi.atlas.profiles.baseline;

import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.trader.cache.ProteomicsBaselineExperimentsCache;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.utils.StringArrayUtil;

import javax.inject.Inject;
import javax.inject.Named;

import static com.google.common.base.Preconditions.checkState;

@Named
@Scope("prototype")
public class ExpressionsRowDeserializerProteomicsBaselineBuilder extends ExpressionsRowDeserializerBaselineBuilder {

    private String experimentAccession;
    private ProteomicsBaselineExperimentsCache experimentsCache;
    private int[] indicesOfAssayGroups;

    @Inject
    public ExpressionsRowDeserializerProteomicsBaselineBuilder(ProteomicsBaselineExperimentsCache experimentsCache) {
        this.experimentsCache = experimentsCache;
    }

    @Override
    public ExpressionsRowDeserializerProteomicsBaselineBuilder forExperiment(String experimentAccession) {
        this.experimentAccession = experimentAccession;
        return this;
    }

    @Override
    public ExpressionsRowDeserializerProteomicsBaselineBuilder withHeaders(String... tsvFileHeaders) {
        this.indicesOfAssayGroups = StringArrayUtil.indicesOf(tsvFileHeaders, "withinSampleAbundance");
        return this;
    }

    @Override
    public ExpressionsRowTsvDeserializerBaseline build() {
        checkState(experimentAccession != null, "Please invoke forExperiment before invoking the build method");

        BaselineExperiment baselineExperiment = experimentsCache.getExperiment(experimentAccession);

        //TODO: ordered factor groups should be passed in from the top, not looked up here
        return new ExpressionsRowTsvDeserializerProteomicsBaseline(baselineExperiment.getExperimentalFactors().getFactorGroupsInOrder(), indicesOfAssayGroups);
    }

}