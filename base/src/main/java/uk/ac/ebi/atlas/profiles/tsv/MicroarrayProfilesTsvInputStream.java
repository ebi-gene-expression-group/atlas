package uk.ac.ebi.atlas.profiles.tsv;

import com.google.common.base.Predicate;
import uk.ac.ebi.atlas.model.experiment.differential.Contrast;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayExpression;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayProfile;

import java.io.Reader;

public class MicroarrayProfilesTsvInputStream extends TsvInputStream<Contrast, MicroarrayExpression, MicroarrayProfile> {


    public MicroarrayProfilesTsvInputStream(Reader reader, Predicate<MicroarrayExpression> expressionFilter,
                                            MicroarrayExperiment experiment) {
        super(reader, new MicroarrayExpressionsRowDeserializerBuilder(experiment), expressionFilter, experiment, 3);
    }

    @Override
    protected MicroarrayProfile nextProfile(String... identifierColumns) {
        return new MicroarrayProfile(identifierColumns[0], identifierColumns[1], identifierColumns[2]);
    }
}
