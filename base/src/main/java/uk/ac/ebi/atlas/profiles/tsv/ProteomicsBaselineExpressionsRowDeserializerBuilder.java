package uk.ac.ebi.atlas.profiles.tsv;

import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.profiles.baseline.ExpressionsRowTsvDeserializerBaseline;
import uk.ac.ebi.atlas.profiles.tsv.RnaSeqBaselineExpressionsRowDeserializerBuilder;
import uk.ac.ebi.atlas.utils.StringArrayUtil;

import javax.inject.Named;

@Named
@Scope("prototype")
public class ProteomicsBaselineExpressionsRowDeserializerBuilder extends RnaSeqBaselineExpressionsRowDeserializerBuilder {

    public ProteomicsBaselineExpressionsRowDeserializerBuilder(BaselineExperiment baselineExperiment) {
        super(baselineExperiment);
    }

    @Override
    public ExpressionsRowTsvDeserializerBaseline build(String... tsvFileHeaders) {

        return new ExpressionsRowTsvDeserializerBaseline(baselineExperiment.getDataColumnDescriptors(), StringArrayUtil.indicesOf
                (tsvFileHeaders, "WithInSampleAbundance"));
    }

}