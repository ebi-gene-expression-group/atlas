
package uk.ac.ebi.atlas.profiles.differential;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.experiment.differential.Contrast;
import uk.ac.ebi.atlas.model.experiment.differential.ContrastTest;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExpression;
import uk.ac.ebi.atlas.model.experiment.differential.Regulation;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(MockitoJUnitRunner.class)
public class IsDifferentialExpressionAboveCutOffTest {

    IsDifferentialExpressionAboveCutOff subject;

    Contrast contrastMock = ContrastTest.get(1).iterator().next();

    DifferentialExpression expression1 = new DifferentialExpression(0.05, 40.0, contrastMock);

    DifferentialExpression expression2 = new DifferentialExpression(0.00005, -40.0, contrastMock);

    @Before
    public void setUp() throws Exception {
        subject = new IsDifferentialExpressionAboveCutOff();
        subject.setPValueCutoff(0.1);
        subject.setRegulation(Regulation.UP_DOWN);
    }

    @Test
    public void testApply() throws Exception {
        assertThat(subject.apply(expression1), is(true));
        assertThat(subject.apply(expression2), is(true));
    }

    @Test
    public void setPValueCutoff() throws Exception {
        subject.setPValueCutoff(0.0001);

        assertThat(subject.apply(expression1), is(false));
        assertThat(subject.apply(expression2), is(true));
    }

    @Test
    public void setFoldChangeCutoff() throws Exception {
        subject.setFoldChangeCutOff(50);

        assertThat(subject.apply(expression1), is(false));
        assertThat(subject.apply(expression2), is(false));
    }

    @Test
    public void testSetRegulation() throws Exception {
        subject.setRegulation(Regulation.DOWN);

        assertThat(subject.apply(expression1), is(false));
        assertThat(subject.apply(expression2), is(true));

        subject.setRegulation(Regulation.UP);

        assertThat(subject.apply(expression1), is(true));
        assertThat(subject.apply(expression2), is(false));
    }
}