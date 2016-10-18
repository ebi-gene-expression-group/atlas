
package uk.ac.ebi.atlas.web;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.profiles.baseline.BaselineExpressionLevelRounder;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(MockitoJUnitRunner.class)
public class ExperimentPageRequestPreferencesTest {

    private ExperimentPageRequestPreferences subject;

    @Mock
    private BaselineExpressionLevelRounder baselineExpressionLevelRounderMock;

    @Before
    public void setUp() throws Exception {
        subject = new BaselineRequestPreferences();
    }

    @Test
    public void cutoffShouldBeRoundedToNoFractionalDigitForValuesLargerThanOne() {
        //given
        subject.setCutoff(2.1211);
        //then
        assertThat(subject.getCutoff(), is(2d));
    }

    @Test
    public void cutoffShouldBeRoundedTo1FractionalDigitForValuesSmallerThanOne() {
        //given
        subject.setCutoff(0.1211);
        //then
        assertThat(subject.getCutoff(), is(0.1d));
    }

    @Test
    public void heatmapMatrixSizeIsSetToTheDefaultRankingSizeIfRequestDoesntSpecifyAnyValue() {
        //given
        subject.setHeatmapMatrixSize(null);
        //then
        assertThat(subject.getHeatmapMatrixSize(), is(ExperimentPageRequestPreferences.DEFAULT_NUMBER_OF_RANKED_GENES));
        //and given
        subject.setHeatmapMatrixSize(33);
        //then
        assertThat(subject.getHeatmapMatrixSize(), is(33));
    }

}
