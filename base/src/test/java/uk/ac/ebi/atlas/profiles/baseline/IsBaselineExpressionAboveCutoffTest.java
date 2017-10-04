package uk.ac.ebi.atlas.profiles.baseline;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExpression;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class IsBaselineExpressionAboveCutoffTest {

    @Mock
    private BaselineExpression expressionMock;

    @Test
    public void applyShouldSucceedIfLevelIsGreaterThanCutoff() throws Exception {
        IsBaselineExpressionAboveCutoff subject =
                new IsBaselineExpressionAboveCutoff();
        subject.setCutoff(1d);

        when(expressionMock.isGreaterThanOrEqual(1d)).thenReturn(true);

        assertThat(subject.test(expressionMock), is(true));
    }

}
