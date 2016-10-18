package uk.ac.ebi.atlas.model.baseline;

import org.junit.Test;
import uk.ac.ebi.atlas.model.baseline.impl.FactorSet;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class BaselineExpressionTest {

    public static final String EXPRESSION_LEVEL_1 = "0";
    public static final String EXPRESSION_LEVEL_2 = "NA";

    private BaselineExpression subject;

    @Test
    public void expressionLevel0 () {
        Factor factor1 = new Factor("ORGANISM_PART", "lung");

        FactorSet factorGroup = new FactorSet();
        factorGroup.add(factor1);

        subject = new BaselineExpression(EXPRESSION_LEVEL_1, factorGroup);

        assertThat(subject.getLevel(), is(Double.valueOf(EXPRESSION_LEVEL_1)));
        assertThat(subject.isKnown(), is(true));

    }

    @Test
    public void expressionLevelNA () {
        Factor factor1 = new Factor("ORGANISM_PART", "lung");

        FactorSet factorGroup = new FactorSet();
        factorGroup.add(factor1);

        subject = new BaselineExpression(EXPRESSION_LEVEL_2, factorGroup);

        assertThat(subject.isKnown(), is(true));
        assertThat(subject.getLevelAsString(), is(EXPRESSION_LEVEL_2));

    }

    @Test
    public void testRemoveTrailingZero() {
        assertThat(BaselineExpression.removeTrailingZero(1.111111), is("1.1111"));
        assertThat(BaselineExpression.removeTrailingZero(1.100111), is("1.1001"));
        assertThat(BaselineExpression.removeTrailingZero(1.111100), is("1.1111"));
        assertThat(BaselineExpression.removeTrailingZero(1.110000), is("1.11"));
    }

}
