package uk.ac.ebi.atlas.profiles.differential.microarray;

import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExpression;
import uk.ac.ebi.atlas.profiles.ExpressionsRowTsvDeserializer;
import uk.ac.ebi.atlas.profiles.differential.ExpressionsRowDeserializerDifferentialBuilder;
import uk.ac.ebi.atlas.trader.cache.MicroarrayExperimentsCache;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
@Scope("prototype")
public class ExpressionsRowDeserializerMicroarrayBuilder extends ExpressionsRowDeserializerDifferentialBuilder<MicroarrayExpression, MicroarrayExperiment> {

    @Inject
    public ExpressionsRowDeserializerMicroarrayBuilder(MicroarrayExperimentsCache experimentsCache) {
        super(experimentsCache);
    }

    @Override
    protected ExpressionsRowTsvDeserializer<MicroarrayExpression> getBufferInstance(List<Contrast> orderedContrasts) {
        return new ExpressionsRowTsvDeserializerMicroarray(orderedContrasts);
    }

}
