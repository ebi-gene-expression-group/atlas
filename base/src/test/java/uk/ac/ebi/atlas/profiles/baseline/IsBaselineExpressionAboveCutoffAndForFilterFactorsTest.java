
package uk.ac.ebi.atlas.profiles.baseline;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExpression;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class IsBaselineExpressionAboveCutoffAndForFilterFactorsTest {

    private IsBaselineExpressionAboveCutoffAndForFilterFactors subject;

    @Mock
    private BaselineExpression expressionMock;

    @Before
    public void init(){
    }

    @Test
    public void applyShouldSucceedIfLevelIsGreaterThanCutoff() throws Exception {

        //given
        subject = new IsBaselineExpressionAboveCutoffAndForFilterFactors();
        subject.setCutoff(1d);

        given(expressionMock.isGreaterThanOrEqual(1d)).willReturn(true);

        //then
        assertThat(subject.apply(expressionMock), is(true));
    }

}
