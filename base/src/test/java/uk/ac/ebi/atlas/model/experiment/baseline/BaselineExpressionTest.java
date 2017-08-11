package uk.ac.ebi.atlas.model.experiment.baseline;

import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class BaselineExpressionTest {

    @Test
    public void testRemoveTrailingZero() {
        assertThat(BaselineExpression.removeTrailingZero(1.111111), is("1.1111"));
        assertThat(BaselineExpression.removeTrailingZero(1.100111), is("1.1001"));
        assertThat(BaselineExpression.removeTrailingZero(1.111100), is("1.1111"));
        assertThat(BaselineExpression.removeTrailingZero(1.110000), is("1.11"));
    }

    @Test
    public void createExpression(){
        String EXPRESSION_LEVEL_1 = "0";
        String EXPRESSION_LEVEL_2 = "123.4";

        Assert.assertThat(
                BaselineExpression.create(EXPRESSION_LEVEL_1).getLevel(),
                is(Double.valueOf(EXPRESSION_LEVEL_1))
        );

        Assert.assertThat(
                BaselineExpression.create(EXPRESSION_LEVEL_2).getLevel(),
                is(Double.valueOf(EXPRESSION_LEVEL_2))
        );

        Assert.assertThat(
                BaselineExpression.create("0"),
                is(BaselineExpression.create("NA"))
        );

    }

}
