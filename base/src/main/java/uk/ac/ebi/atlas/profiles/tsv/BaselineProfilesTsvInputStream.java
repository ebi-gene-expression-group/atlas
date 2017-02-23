package uk.ac.ebi.atlas.profiles.tsv;

import com.google.common.base.Predicate;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExpression;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineProfile;
import uk.ac.ebi.atlas.profiles.tsv.ProteomicsBaselineExpressionsRowDeserializerBuilder;
import uk.ac.ebi.atlas.profiles.tsv.RnaSeqBaselineExpressionsRowDeserializerBuilder;
import uk.ac.ebi.atlas.profiles.tsv.TsvInputStream;

import java.io.Reader;

public class BaselineProfilesTsvInputStream extends TsvInputStream<AssayGroup, BaselineExpression, BaselineProfile> {

    public BaselineProfilesTsvInputStream(Reader reader,
                                             Predicate<BaselineExpression> expressionFilter,
                                             BaselineExperiment experiment) {
        super(reader, experiment.getType().isProteomicsBaseline() ?
                new ProteomicsBaselineExpressionsRowDeserializerBuilder(experiment)
                : new RnaSeqBaselineExpressionsRowDeserializerBuilder(experiment), expressionFilter, experiment, 2);
    }


    @Override
    protected BaselineProfile nextProfile(String... identifierColumns) {
        return new BaselineProfile(identifierColumns[0], identifierColumns[1]);
    }

}
