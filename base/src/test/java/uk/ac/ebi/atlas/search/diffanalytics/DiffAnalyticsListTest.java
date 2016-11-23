package uk.ac.ebi.atlas.search.diffanalytics;

import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.experiment.differential.Contrast;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExpression;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class DiffAnalyticsListTest {

    private DiffAnalyticsList subject;

    @Mock
    private DiffAnalytics diffAnalytics1;
    @Mock
    private DiffAnalytics diffAnalytics2;
    @Mock
    private DiffAnalytics diffAnalytics3;

    private DifferentialExpression expression1;
    private DifferentialExpression expression2;
    private DifferentialExpression expression3;

    @Mock
    private Contrast contrast;


    public void initSubject() throws Exception {
        given(diffAnalytics1.getExpression()).willReturn(expression1);
        given(diffAnalytics2.getExpression()).willReturn(expression2);
        given(diffAnalytics3.getExpression()).willReturn(expression3);

        List<DiffAnalytics> expressions = Lists.newArrayList(diffAnalytics1, diffAnalytics2, diffAnalytics3);

        subject = new DiffAnalyticsList(expressions, 3);
    }

    @Test
    public void maxUpRegulatedExpressionLevelShouldBeZeroWhenAllProfilesHaveNoUpRegulatedExpressionLevel() throws Exception {
        expression1 = new DifferentialExpression(0,0,contrast);
        expression2 = new DifferentialExpression(0,0,contrast);
        expression3 = new DifferentialExpression(0,0,contrast);

        initSubject();

        //
        assertThat(subject.getMaxUpRegulatedExpressionLevel(), is(0D));
    }

    @Test
    public void maxUpRegulatedExpressionLevelShouldReturnTheMaxUpRegulatedExpressionLevelAcrossAllProfiiles() throws Exception {
        expression1 = new DifferentialExpression(0,23.3,contrast);
        expression2 = new DifferentialExpression(0,0.22,contrast);
        expression3 = new DifferentialExpression(0,0,contrast);

        initSubject();

        //
        assertThat(subject.getMaxUpRegulatedExpressionLevel(), is(23.3D));
    }

    @Test
    public void minUpRegulatedExpressionLevelShouldReturnTheMinUpRegulatedExpressionLevelAcrossAllProfiiles() throws Exception {
        expression1 = new DifferentialExpression(0,23,contrast);
        expression2 = new DifferentialExpression(0,3,contrast);
        expression3 = new DifferentialExpression(0,0.001,contrast);

        initSubject();

        //
        assertThat(subject.getMinUpRegulatedExpressionLevel(), is(0.001D));
    }

    @Test
    public void maxDownRegulatedExpressionLevelShouldBeZeroWhenAllProfilesHaveNoDownRegulatedExpressionLevel() throws Exception {
        expression1 = new DifferentialExpression(0,0,contrast);
        expression2 = new DifferentialExpression(0,0,contrast);
        expression3 = new DifferentialExpression(0,0,contrast);

        initSubject();

        //
        assertThat(subject.getMaxDownRegulatedExpressionLevel(), is(0D));
    }

    @Test
    public void maxDownRegulatedExpressionLevelShouldReturnTheMaxDownRegulatedExpressionLevelAcrossAllProfiiles() throws Exception {
        //given
        expression1 = new DifferentialExpression(0,-23.3,contrast);
        expression2 = new DifferentialExpression(0,-0.22,contrast);
        expression3 = new DifferentialExpression(0,0,contrast);

        initSubject();

        //
        assertThat(subject.getMaxDownRegulatedExpressionLevel(), is(-23.3D));
    }

    @Test
    public void minDownRegulatedExpressionLevelShouldReturnTheMinDownRegulatedExpressionLevelAcrossAllProfiiles() throws Exception {
        //given
        expression1 = new DifferentialExpression(0,-23,contrast);
        expression2 = new DifferentialExpression(0,-3,contrast);
        expression3 = new DifferentialExpression(0,-0.001,contrast);

        initSubject();

        //
        assertThat(subject.getMinDownRegulatedExpressionLevel(), is(-0.001D));
    }

}
