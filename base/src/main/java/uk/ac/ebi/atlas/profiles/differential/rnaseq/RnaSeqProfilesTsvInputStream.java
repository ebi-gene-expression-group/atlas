package uk.ac.ebi.atlas.profiles.differential.rnaseq;

import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExpression;
import uk.ac.ebi.atlas.profiles.TsvInputStream;
import uk.ac.ebi.atlas.model.experiment.differential.rnaseq.RnaSeqProfile;

import java.io.Reader;

public class RnaSeqProfilesTsvInputStream extends TsvInputStream<RnaSeqProfile, DifferentialExpression> {

    private RnaSeqProfileReusableBuilder rnaSeqProfileReusableBuilder;

    public RnaSeqProfilesTsvInputStream(Reader reader,
                                        ExpressionsRowDeserializerRnaSeqBuilder expressionsRowDeserializerRnaSeqBuilder,
                                        RnaSeqProfileReusableBuilder rnaSeqProfileReusableBuilder) {

        super(reader, expressionsRowDeserializerRnaSeqBuilder);
        this.rnaSeqProfileReusableBuilder = rnaSeqProfileReusableBuilder;
    }

    @Override
    public RnaSeqProfile createProfile() {
        RnaSeqProfile profile = rnaSeqProfileReusableBuilder.create();
        return profile.isEmpty() ? null : profile;
    }

    @Override
    public void addExpressionToBuilder(DifferentialExpression expression) {
        rnaSeqProfileReusableBuilder.addExpression(expression);
    }

    @Override
    public void addGeneInfoValueToBuilder(String[] values) {
        rnaSeqProfileReusableBuilder.beginNewInstance(values[0], values[1]);
    }

}
