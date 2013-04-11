package uk.ac.ebi.atlas.commands.download;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.geneannotation.GeneNamesProvider;
import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.utils.CsvReaderBuilder;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AnalyticsDataWriterTest {

    @Mock
    private DifferentialExperiment experimentMock;

    private AnalyticsDataWriter subject;

    private final static String[] HEADER = {"Gene", "c1.p-value", "c1.t-stat", "c2.p-value", "c2.t-stat"};

    @Before
    public void initSubject() throws Exception {
        Contrast contrastMock1 = mock(Contrast.class);
        Contrast contrastMock2 = mock(Contrast.class);

        when(contrastMock1.getDisplayName()).thenReturn("contrast1");
        when(contrastMock2.getDisplayName()).thenReturn("contrast2");

        when(experimentMock.getContrast("c1")).thenReturn(contrastMock1);
        when(experimentMock.getContrast("c2")).thenReturn(contrastMock2);

        CsvReaderBuilder csvReaderBuilderMock = mock(CsvReaderBuilder.class);
        GeneNamesProvider geneNamesProviderMock = mock(GeneNamesProvider.class);

        subject = new AnalyticsDataWriter(csvReaderBuilderMock, geneNamesProviderMock);
        subject.setExperiment(experimentMock);

    }

    @Test
    public void testBuildHeader() throws Exception {
        String[] newHeader = subject.buildHeader(HEADER);
        assertThat(newHeader, is(new String[]{DifferentialExperimentFullDataWriter.GENE_NAME
                , DifferentialExperimentFullDataWriter.GENE_ID
        , "contrast1.p-value", "contrast1.t-stat", "contrast2.p-value", "contrast2.t-stat"}));
    }

    @Test
    public void testReplaceContrastIdWithName() throws Exception {
        String columnName = subject.replaceContrastIdWithName("c1.p-value");
        assertThat(columnName, is("contrast1.p-value"));
    }
}
