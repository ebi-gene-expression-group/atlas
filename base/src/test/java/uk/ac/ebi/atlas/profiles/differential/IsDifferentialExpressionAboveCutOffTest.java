package uk.ac.ebi.atlas.profiles.differential;

import org.junit.Before;
import org.junit.Test;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExpression;
import uk.ac.ebi.atlas.model.experiment.differential.Regulation;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class IsDifferentialExpressionAboveCutOffTest {

    IsDifferentialExpressionAboveCutOff<DifferentialExpression> subject;

    DifferentialExpression expression1 = new DifferentialExpression(0.05, 40.0);

    DifferentialExpression expression2 = new DifferentialExpression(0.00005, -40.0);

    @Before
    public void setUp() throws Exception {
        subject = new IsDifferentialExpressionAboveCutOff<>();
        subject.setPValueCutoff(0.1);
        subject.setRegulation(Regulation.UP_DOWN);
    }

    @Test
    public void testApply() throws Exception {
        assertThat(subject.test(expression1), is(true));
        assertThat(subject.test(expression2), is(true));
    }

    @Test
    public void setPValueCutoff() throws Exception {
        subject.setPValueCutoff(0.0001);

        assertThat(subject.test(expression1), is(false));
        assertThat(subject.test(expression2), is(true));
    }

    @Test
    public void setFoldChangeCutoff() throws Exception {
        subject.setFoldChangeCutOff(50);

        assertThat(subject.test(expression1), is(false));
        assertThat(subject.test(expression2), is(false));
    }

    @Test
    public void testSetRegulation() throws Exception {
        subject.setRegulation(Regulation.DOWN);

        assertThat(subject.test(expression1), is(false));
        assertThat(subject.test(expression2), is(true));

        subject.setRegulation(Regulation.UP);

        assertThat(subject.test(expression1), is(true));
        assertThat(subject.test(expression2), is(false));
    }
}