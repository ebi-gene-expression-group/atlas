package uk.ac.ebi.atlas.profiles.tsv;

import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExpression;
import uk.ac.ebi.atlas.profiles.tsv.DifferentialExpressionsRowDeserializerBuilder;
import uk.ac.ebi.atlas.model.experiment.differential.Contrast;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.profiles.tsv.DifferentialExpressionsRowDeserializer;
import uk.ac.ebi.atlas.profiles.tsv.ExpressionsRowDeserializer;
import uk.ac.ebi.atlas.profiles.tsv.RnaSeqDifferentialExpressionsRowDeserializer;

import java.util.List;

public class RnaSeqDifferentialExpressionsRowDeserializerBuilder extends DifferentialExpressionsRowDeserializerBuilder<DifferentialExpression> {

    public RnaSeqDifferentialExpressionsRowDeserializerBuilder(DifferentialExperiment experiment) {
        super(experiment);
    }

    @Override
    protected ExpressionsRowDeserializer<DifferentialExpression> getBufferInstance(List<Contrast> orderedContrasts) {
        return new RnaSeqDifferentialExpressionsRowDeserializer(orderedContrasts);
    }

}