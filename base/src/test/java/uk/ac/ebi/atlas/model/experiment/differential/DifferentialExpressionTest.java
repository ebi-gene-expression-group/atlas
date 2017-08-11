package uk.ac.ebi.atlas.model.experiment.differential;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(MockitoJUnitRunner.class)
public class DifferentialExpressionTest {

    public static final double P_VALUE = 0.0005;
    public static final double FOLD_CHANGE = 42.0;
    public static final double SMALL_P_VALUE = 1.17501162847487E-242;

    @Mock
    Contrast contrastMock;

    DifferentialExpression subject;

    @Before
    public void setUp() throws Exception {
        subject = new DifferentialExpression(P_VALUE, FOLD_CHANGE);
    }

    @Test
    public void testGetFoldChange() {
        assertThat(subject.getFoldChange(), is(FOLD_CHANGE));
    }

    @Test
    public void testGetLevel() {
        assertThat(subject.getLevel(), is(FOLD_CHANGE));
    }

    @Test
    public void testGetPValue() {
        assertThat(subject.getPValue(), is(P_VALUE));
    }

    @Test
    public void testEquals() {
        assertThat(subject.equals(new DifferentialExpression(P_VALUE, FOLD_CHANGE)), is(true));
    }

    @Test
    public void testIsOverExpressed() {
        assertThat(subject.isOverExpressed(), is(true));
    }

    @Test
    public void testIsUnderExpressed() {
        assertThat(subject.isUnderExpressed(), is(false));
    }

    @Test
    public void testUnderExpressedGeneIsForRegulation() throws Exception {
        //when
        DifferentialExpression expression = new DifferentialExpression(1.0, -1.0);

        //then
        assertThat(expression.isRegulatedLike(Regulation.UP_DOWN), is(true));
        assertThat(expression.isRegulatedLike(Regulation.UP), is(false));
        assertThat(expression.isRegulatedLike(Regulation.DOWN), is(true));
    }

    @Test
    public void testOverExpressedGeneIsForRegulation() throws Exception {
        //when
        DifferentialExpression expression = new DifferentialExpression(1.0, 1.0);

        //then
        assertThat(expression.isRegulatedLike(Regulation.UP_DOWN), is(true));
        assertThat(expression.isRegulatedLike(Regulation.UP), is(true));
        assertThat(expression.isRegulatedLike(Regulation.DOWN), is(false));
    }

    @Test
    public void testSmallPValue() {
        //when
        DifferentialExpression expression = new DifferentialExpression(SMALL_P_VALUE, -1.0);

        //then
        assertThat(expression.getPValue(), is(0D));
    }

}
