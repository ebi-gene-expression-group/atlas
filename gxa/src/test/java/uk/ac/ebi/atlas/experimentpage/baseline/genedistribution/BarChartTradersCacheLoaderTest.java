
package uk.ac.ebi.atlas.experimentpage.baseline.genedistribution;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BarChartTradersCacheLoaderTest {

    public static final String EXPERIMENT_ACCESSION = "experimentAccession";

    @Mock
    private BarChartTraderBuilder barChartTraderBuilderMock;

    @Mock
    private BarChartTrader barChartTraderMock;

    @Mock
    private BarChartTradersCacheLoader subject;

    @Before
    public void setUp() throws Exception {
        when(subject.createBarChartTraderBuilder()).thenReturn(barChartTraderBuilderMock);
        when(barChartTraderBuilderMock.forExperiment(EXPERIMENT_ACCESSION)).thenReturn(barChartTraderBuilderMock);
        when(barChartTraderBuilderMock.create()).thenReturn(barChartTraderMock);
    }

    @Test
    public void testLoad() throws Exception {
        doCallRealMethod().when(subject).load(EXPERIMENT_ACCESSION);

        assertThat(subject.load(EXPERIMENT_ACCESSION), is(barChartTraderMock));
    }
}