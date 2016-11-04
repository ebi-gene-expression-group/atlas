package uk.ac.ebi.atlas.profiles.differential.rnaseq;

import uk.ac.ebi.atlas.model.differential.DifferentialExpression;
import uk.ac.ebi.atlas.profiles.differential.ExpressionsRowDeserializerDifferentialBuilder;
import uk.ac.ebi.atlas.trader.cache.RnaSeqDiffExperimentsCache;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.profiles.ExpressionsRowTsvDeserializer;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
@Scope("prototype")
public class ExpressionsRowDeserializerRnaSeqBuilder extends ExpressionsRowDeserializerDifferentialBuilder<DifferentialExpression, DifferentialExperiment> {

    @Inject
    public ExpressionsRowDeserializerRnaSeqBuilder(RnaSeqDiffExperimentsCache experimentsCache) {
        super(experimentsCache);

    }

    @Override
    protected ExpressionsRowTsvDeserializer<DifferentialExpression> getBufferInstance(List<Contrast> orderedContrasts) {
        return new ExpressionsRowTsvDeserializerRnaSeq(orderedContrasts);
    }

}