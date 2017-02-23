package uk.ac.ebi.atlas.profiles.tsv;

import com.google.common.base.Predicate;
import uk.ac.ebi.atlas.model.experiment.differential.Contrast;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExpression;
import uk.ac.ebi.atlas.model.experiment.differential.rnaseq.RnaSeqProfile;

import java.io.Reader;

public class RnaSeqDifferentialProfilesTsvInputStream extends TsvInputStream<Contrast, DifferentialExpression, RnaSeqProfile> {

    public RnaSeqDifferentialProfilesTsvInputStream(Reader reader,
                                                       Predicate<DifferentialExpression> expressionFilter, DifferentialExperiment experiment) {
        super(reader, new RnaSeqDifferentialExpressionsRowDeserializerBuilder(experiment), expressionFilter, experiment, 2);
    }

    @Override
    protected RnaSeqProfile nextProfile(String... identifierColumns) {
        return new RnaSeqProfile(identifierColumns[0], identifierColumns[1]);
    }
}
