package uk.ac.ebi.atlas.model.experiment.differential.microarray;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(MockitoJUnitRunner.class)
public class MicroarrayExpressionTest {
    private static final double P_VALUE = 0.05;
    private static final int FOLD_CHANGE = 14;
    private static final double TSTATISTIC = 0.6;

    private static final double SMALLPVALUE = 1.17501162847487E-242;

    private MicroarrayExpression subject;

    @Before
    public void setUp() throws Exception {
        subject = new MicroarrayExpression(P_VALUE, FOLD_CHANGE, TSTATISTIC);
    }

    @Test
    public void testGetTstatistic() {
        assertThat(subject.getTstatistic(), is(TSTATISTIC));
    }

    @Test
    public void testSmallPValue() {
        //when
        MicroarrayExpression expression = new MicroarrayExpression(SMALLPVALUE, -1.0, TSTATISTIC);

        //then
        assertThat(expression.getPValue(), is(0D));
    }
}
