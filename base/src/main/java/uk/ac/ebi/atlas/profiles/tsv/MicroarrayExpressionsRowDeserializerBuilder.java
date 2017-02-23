package uk.ac.ebi.atlas.profiles.tsv;

import uk.ac.ebi.atlas.model.experiment.differential.Contrast;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayExpression;
import uk.ac.ebi.atlas.profiles.tsv.DifferentialExpressionsRowDeserializerBuilder;
import uk.ac.ebi.atlas.profiles.tsv.ExpressionsRowDeserializer;
import uk.ac.ebi.atlas.profiles.tsv.MicroarrayExpressionsRowDeserializer;

import java.util.List;

public class MicroarrayExpressionsRowDeserializerBuilder extends DifferentialExpressionsRowDeserializerBuilder<MicroarrayExpression> {


    public MicroarrayExpressionsRowDeserializerBuilder(MicroarrayExperiment experiment) {
        super(experiment);
    }

    @Override
    protected ExpressionsRowDeserializer<MicroarrayExpression> getBufferInstance(List<Contrast> orderedContrasts) {
        return new MicroarrayExpressionsRowDeserializer(orderedContrasts);
    }

}
