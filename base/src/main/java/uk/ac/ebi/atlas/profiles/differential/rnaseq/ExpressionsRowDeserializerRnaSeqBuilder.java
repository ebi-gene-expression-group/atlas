package uk.ac.ebi.atlas.profiles.differential.rnaseq;

import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExpression;
import uk.ac.ebi.atlas.profiles.differential.ExpressionsRowDeserializerDifferentialBuilder;
import uk.ac.ebi.atlas.model.experiment.differential.Contrast;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.profiles.ExpressionsRowTsvDeserializer;

import java.util.List;

public class ExpressionsRowDeserializerRnaSeqBuilder extends ExpressionsRowDeserializerDifferentialBuilder<DifferentialExpression, DifferentialExperiment> {

    public ExpressionsRowDeserializerRnaSeqBuilder(DifferentialExperiment experiment) {
        super(experiment);
    }

    @Override
    protected ExpressionsRowTsvDeserializer<DifferentialExpression> getBufferInstance(List<Contrast> orderedContrasts) {
        return new ExpressionsRowTsvDeserializerRnaSeq(orderedContrasts);
    }

}