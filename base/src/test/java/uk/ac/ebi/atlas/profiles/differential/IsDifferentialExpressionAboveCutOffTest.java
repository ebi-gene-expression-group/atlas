package uk.ac.ebi.atlas.profiles.differential;

import org.junit.Before;
import org.junit.Test;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExpression;
import uk.ac.ebi.atlas.model.experiment.differential.Regulation;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class IsDifferentialExpressionAboveCutOffTest {
    private static final DifferentialExpression EXPRESSION_1 = new DifferentialExpression(0.05, 40.0);
    private static final DifferentialExpression EXPRESSION_2 = new DifferentialExpression(0.00005, -40.0);

    private IsDifferentialExpressionAboveCutOff<DifferentialExpression> subject;

    @Before
    public void setUp() throws Exception {
        subject = new IsDifferentialExpressionAboveCutOff<>();
        subject.setPValueCutoff(0.1);
        subject.setRegulation(Regulation.UP_DOWN);
    }

    @Test
    public void testApply() {
        assertThat(subject.test(EXPRESSION_1), is(true));
        assertThat(subject.test(EXPRESSION_2), is(true));
    }

    @Test
    public void setPValueCutoff() {
        subject.setPValueCutoff(0.0001);

        assertThat(subject.test(EXPRESSION_1), is(false));
        assertThat(subject.test(EXPRESSION_2), is(true));
    }

    @Test
    public void setFoldChangeCutoff() {
        subject.setFoldChangeCutOff(50);

        assertThat(subject.test(EXPRESSION_1), is(false));
        assertThat(subject.test(EXPRESSION_2), is(false));
    }

    @Test
    public void testSetRegulation() {
        subject.setRegulation(Regulation.DOWN);

        assertThat(subject.test(EXPRESSION_1), is(false));
        assertThat(subject.test(EXPRESSION_2), is(true));

        subject.setRegulation(Regulation.UP);

        assertThat(subject.test(EXPRESSION_1), is(true));
        assertThat(subject.test(EXPRESSION_2), is(false));
    }
}
