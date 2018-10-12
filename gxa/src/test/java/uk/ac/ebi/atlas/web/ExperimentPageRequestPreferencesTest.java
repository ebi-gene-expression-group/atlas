package uk.ac.ebi.atlas.web;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(MockitoJUnitRunner.class)
public class ExperimentPageRequestPreferencesTest {
    private ExperimentPageRequestPreferences subject;

    @Before
    public void setUp() {
        subject = new RnaSeqBaselineRequestPreferences();
    }

    @Test
    public void heatmapMatrixSizeIsSetToTheDefaultRankingSizeIfRequestDoesntSpecifyAnyValue() {
        assertThat(
                subject.getHeatmapMatrixSize(),
                is(ExperimentPageRequestPreferences.DEFAULT_NUMBER_OF_RANKED_GENES));
        //and given
        subject.setHeatmapMatrixSize(33);
        //then
        assertThat(subject.getHeatmapMatrixSize(), is(33));
    }
}
