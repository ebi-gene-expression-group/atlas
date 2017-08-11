package uk.ac.ebi.atlas.model.experiment.baseline;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ExpressionTest {

    private BaselineExpression subject;

    @Before
    public void setUp() {
        subject = new BaselineExpression(2.3);
    }

    @Test
    public void testIsGreaterThanOrEquals() {
        assertThat(subject.isGreaterThanOrEqual(0.0), is(true));
        assertThat(subject.isGreaterThanOrEqual(3.0), is(false));
        assertThat(subject.isGreaterThanOrEqual(2.3), is(true));
    }
}
