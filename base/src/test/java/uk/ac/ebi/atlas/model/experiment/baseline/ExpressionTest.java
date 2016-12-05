package uk.ac.ebi.atlas.model.experiment.baseline;

import org.junit.Before;
import org.junit.Test;
import uk.ac.ebi.atlas.model.experiment.baseline.impl.FactorSet;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ExpressionTest {

    private BaselineExpression subject;

    @Before
    public void setUp() {
        Factor factor = new Factor("aType", "heart");
        subject = new BaselineExpression(2.3, new FactorSet().add(factor));
    }

    @Test
    public void testIsGreaterThanOrEquals() {
        assertThat(subject.isGreaterThanOrEqual(0.0), is(true));
        assertThat(subject.isGreaterThanOrEqual(3.0), is(false));
        assertThat(subject.isGreaterThanOrEqual(2.3), is(true));
    }
}
