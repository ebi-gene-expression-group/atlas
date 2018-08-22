package uk.ac.ebi.atlas.model.experiment.differential;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class DifferentialExpressionTest {
    private  static final double P_VALUE = 0.0005;
    private  static final double FOLD_CHANGE = 42.0;
    private  static final double SMALL_P_VALUE = 1.17501162847487E-242;

    private DifferentialExpression subject;

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
    public void testUnderExpressedGeneIsForRegulation() {
        DifferentialExpression expression = new DifferentialExpression(1.0, -1.0);

        assertThat(expression.isRegulatedLike(Regulation.UP_DOWN), is(true));
        assertThat(expression.isRegulatedLike(Regulation.UP), is(false));
        assertThat(expression.isRegulatedLike(Regulation.DOWN), is(true));
    }

    @Test
    public void testOverExpressedGeneIsForRegulation() {
        DifferentialExpression expression = new DifferentialExpression(1.0, 1.0);

        assertThat(expression.isRegulatedLike(Regulation.UP_DOWN), is(true));
        assertThat(expression.isRegulatedLike(Regulation.UP), is(true));
        assertThat(expression.isRegulatedLike(Regulation.DOWN), is(false));
    }

    @Test
    public void testSmallPValue() {
        DifferentialExpression expression = new DifferentialExpression(SMALL_P_VALUE, -1.0);

        assertThat(expression.getPValue(), is(0D));
    }
}
