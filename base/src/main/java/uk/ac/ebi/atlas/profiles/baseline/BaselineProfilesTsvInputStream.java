package uk.ac.ebi.atlas.profiles.baseline;

import uk.ac.ebi.atlas.model.baseline.BaselineProfile;
import uk.ac.ebi.atlas.profiles.TsvInputStream;
import uk.ac.ebi.atlas.model.baseline.BaselineExpression;

import java.io.Reader;

public class BaselineProfilesTsvInputStream extends TsvInputStream<BaselineProfile, BaselineExpression> {

    private BaselineProfileReusableBuilder baselineProfileReusableBuilder;


    public BaselineProfilesTsvInputStream(Reader reader,
                                          ExpressionsRowDeserializerBaselineBuilder expressionsRowDeserializerBaselineBuilder,
                                          BaselineProfileReusableBuilder baselineProfileReusableBuilder) {

        super(reader, expressionsRowDeserializerBaselineBuilder);
        this.baselineProfileReusableBuilder = baselineProfileReusableBuilder;
    }

    @Override
    public BaselineProfile createProfile() {
        BaselineProfile baselineProfile = baselineProfileReusableBuilder.create();
        return baselineProfile.isEmpty() ? null : baselineProfile;
    }

    @Override
    public void addExpressionToBuilder(BaselineExpression expression) {
        baselineProfileReusableBuilder.addExpression(expression);
    }

    @Override
    public void addGeneInfoValueToBuilder(String[] values) {
        baselineProfileReusableBuilder.beginNewInstance(values[0], values[1]);
    }

}
