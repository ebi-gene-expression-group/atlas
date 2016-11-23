
package uk.ac.ebi.atlas.model.experiment.baseline;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.experiment.baseline.Factor;
import uk.ac.ebi.atlas.model.experiment.baseline.impl.FactorSet;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExpression;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(MockitoJUnitRunner.class)
public class ExpressionTest {

    private BaselineExpression subject;

    @Before
    public void initSubject() {

        Factor factor = new Factor("aType", "heart");

        subject = new BaselineExpression(2.3, new FactorSet().add(factor));
    }

    @Test
    public void testIsGreaterThan() throws Exception {

        assertThat(subject.isGreaterThan(0.0), is(true));

        assertThat(subject.isGreaterThan(3.0), is(false));

        assertThat(subject.isGreaterThan(2.3), is(false));

    }
}
