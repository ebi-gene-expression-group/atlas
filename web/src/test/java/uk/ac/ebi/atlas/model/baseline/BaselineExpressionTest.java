package uk.ac.ebi.atlas.model.baseline;

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

}
