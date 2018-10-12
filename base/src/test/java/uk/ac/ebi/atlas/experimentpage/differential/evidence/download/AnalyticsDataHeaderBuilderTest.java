package uk.ac.ebi.atlas.experimentpage.differential.evidence.download;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import uk.ac.ebi.atlas.experimentpage.differential.download.AnalyticsDataHeaderBuilder;
import uk.ac.ebi.atlas.model.experiment.differential.Contrast;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExperiment;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AnalyticsDataHeaderBuilderTest {
    @Mock
    private DifferentialExperiment experimentMock;

    private AnalyticsDataHeaderBuilder subject;

    private static final String[] HEADER =
            {"Gene ID", "Gene Name", "c1.p-value", "c1.t-stat", "c2.p-value", "c2.t-stat"};

    @Before
    public void initSubject() {
        Contrast contrastMock1 = mock(Contrast.class);
        Contrast contrastMock2 = mock(Contrast.class);

        when(contrastMock1.getDisplayName()).thenReturn("contrast1");
        when(contrastMock2.getDisplayName()).thenReturn("contrast2");

        when(experimentMock.getDataColumnDescriptor("c1")).thenReturn(contrastMock1);
        when(experimentMock.getDataColumnDescriptor("c2")).thenReturn(contrastMock2);

        subject = new AnalyticsDataHeaderBuilder(experimentMock);
    }

    @Test
    public void testBuildHeader() {
        String[] newHeader = subject.apply(HEADER);
        assertThat(
                newHeader,
                is(new String[] {
                        "Gene ID", "Gene Name",
                        "contrast1.p-value", "contrast1.t-stat", "contrast2.p-value", "contrast2.t-stat"}));
    }

    @Test
    public void testReplaceContrastIdWithName() {
        String columnName = subject.replaceContrastIdWithName("c1.p-value");
        assertThat(columnName, is("contrast1.p-value"));
    }
}
