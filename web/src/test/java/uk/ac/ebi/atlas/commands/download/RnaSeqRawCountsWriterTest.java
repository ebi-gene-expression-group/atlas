package uk.ac.ebi.atlas.commands.download;

import au.com.bytecode.opencsv.CSVReader;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.geneannotation.GeneNamesProvider;
import uk.ac.ebi.atlas.utils.CsvReaderBuilder;

import java.io.PrintWriter;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RnaSeqRawCountsWriterTest {

    @Mock
    private CsvReaderBuilder csvReaderBuilderMock;

    @Mock
    private PrintWriter printWriterMock;

    @Mock
    private GeneNamesProvider geneNamesProviderMock;

    @Mock
    private CSVReader csvReaderMock;

    private String[] header = {"Gene", "SRR057596", "SRR057597", "SRR057598"};
    private String[] line = {"ens1", "1", "0", "10.5"};

    private DifferentialExperimentFullDataWriter subject;

    @Before
    public void initSubject() throws Exception {
        when(csvReaderBuilderMock.buildCsvReader(anyString())).thenReturn(csvReaderMock);
        when(csvReaderMock.readNext()).thenReturn(header)
                .thenReturn(line)
                .thenReturn(null);

        when(geneNamesProviderMock.getGeneName("ens1")).thenReturn("name1");

        subject = new DifferentialExperimentFullDataWriter(csvReaderBuilderMock, geneNamesProviderMock);
        subject.setResponseWriter(printWriterMock);
        subject.setFileUrlTemplate("magetab/{0}/{0}-row-counts.tsv");
    }

    @Test
    public void testBuildHeader() throws Exception {
        String[] result = subject.buildHeader(header);
        assertThat(result, is(new String[]{DifferentialExperimentFullDataWriter.GENE_NAME, DifferentialExperimentFullDataWriter.GENE_ID, "SRR057596", "SRR057597", "SRR057598"}));
    }

    @Test
    public void testWrite() throws Exception {
        Long count = subject.write("Exp1");

        verify(printWriterMock).write("Gene name\tGene Id\tSRR057596\tSRR057597\tSRR057598\n", 0, 48);

        verify(printWriterMock).write("name1\tens1\t1\t0\t10.5\n", 0, 20);

        assertThat(count, is(1L));
    }
}
