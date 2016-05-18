
package uk.ac.ebi.atlas.model.differential.microarray;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.differential.Contrast;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(MockitoJUnitRunner.class)
public class MicroarrayExpressionTest {

    public static final double P_VALUE = 0.05;
    public static final int FOLD_CHANGE = 14;
    public static final double TSTATISTIC = 0.6;

    public static final double SMALLPVALUE = 1.17501162847487E-242;

    @Mock
    Contrast contrastMock;

    MicroarrayExpression subject;

    @Before
    public void setUp() throws Exception {
        subject = new MicroarrayExpression(P_VALUE, FOLD_CHANGE, TSTATISTIC, contrastMock);
    }

    @Test
    public void testGetTstatistic() throws Exception {
        assertThat(subject.getTstatistic(), is(TSTATISTIC));
    }

    @Test
    public void testSmallPValue() {
        //when
        MicroarrayExpression expression = new MicroarrayExpression(SMALLPVALUE, -1.0, TSTATISTIC, contrastMock);

        //then
        assertThat(expression.getPValue(), is(0D));
    }
}