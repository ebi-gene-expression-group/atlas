package uk.ac.ebi.atlas.profiles.baseline;

import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.utils.StringArrayUtil;

import javax.inject.Named;

@Named
@Scope("prototype")
public class ExpressionsRowDeserializerProteomicsBaselineBuilder extends ExpressionsRowDeserializerBaselineBuilder {

    public ExpressionsRowDeserializerProteomicsBaselineBuilder(BaselineExperiment baselineExperiment) {
        super(baselineExperiment);
    }

    @Override
    public ExpressionsRowTsvDeserializerBaseline build(String... tsvFileHeaders) {

        return new ExpressionsRowTsvDeserializerProteomicsBaseline(baselineExperiment.getExperimentalFactors().getFactorGroupsInOrder(), StringArrayUtil.indicesOf(tsvFileHeaders, "WithInSampleAbundance"));
    }

}