
package uk.ac.ebi.atlas.profiles.baseline;

import uk.ac.ebi.atlas.model.baseline.BaselineExpression;
import uk.ac.ebi.atlas.model.baseline.BaselineProfile;
import uk.ac.ebi.atlas.profiles.BaselineExpressionsKryoReader;
import uk.ac.ebi.atlas.profiles.KryoInputStream;

public class BaselineProfilesKryoInputStream extends KryoInputStream<BaselineProfile, BaselineExpression> {

    private BaselineProfileReusableBuilder baselineProfileReusableBuilder;


    public BaselineProfilesKryoInputStream(BaselineExpressionsKryoReader baselineExpressionsKryoReader, String experimentAccession,
                                           ExpressionsRowRawDeserializerBaselineBuilder expressionsRowDeserializerBaselineBuilder,
                                           BaselineProfileReusableBuilder baselineProfileReusableBuilder) {

        super(baselineExpressionsKryoReader, experimentAccession, expressionsRowDeserializerBaselineBuilder);
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
