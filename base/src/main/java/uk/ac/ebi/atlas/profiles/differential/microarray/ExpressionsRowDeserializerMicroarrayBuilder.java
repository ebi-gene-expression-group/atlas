package uk.ac.ebi.atlas.profiles.differential.microarray;

import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExpression;
import uk.ac.ebi.atlas.profiles.ExpressionsRowTsvDeserializer;
import uk.ac.ebi.atlas.profiles.differential.ExpressionsRowDeserializerDifferentialBuilder;

import javax.inject.Named;
import java.util.List;

@Named
@Scope("prototype")
public class ExpressionsRowDeserializerMicroarrayBuilder extends ExpressionsRowDeserializerDifferentialBuilder<MicroarrayExpression, MicroarrayExperiment> {


    public ExpressionsRowDeserializerMicroarrayBuilder(MicroarrayExperiment experiment) {
        super(experiment);
    }

    @Override
    protected ExpressionsRowTsvDeserializer<MicroarrayExpression> getBufferInstance(List<Contrast> orderedContrasts) {
        return new ExpressionsRowTsvDeserializerMicroarray(orderedContrasts);
    }

}
