package uk.ac.ebi.atlas.commands.download;

import au.com.bytecode.opencsv.CSVReader;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.geneannotation.GeneNamesProvider;
import uk.ac.ebi.atlas.utils.TsvReaderUtils;

import java.io.PrintWriter;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ExpressionsWriterImplTest {

    @Mock
    private TsvReaderUtils tsvReaderUtilsMock;

    @Mock
    private PrintWriter printWriterMock;

    @Mock
    private GeneNamesProvider geneNamesProviderMock;

    @Mock
    private CSVReader csvReaderMock;

    private String[] header = {"Gene", "SRR057596", "SRR057597", "SRR057598"};
    private String[] line = {"ens1", "1", "0", "10.5"};

    private ExpressionsWriterImpl subject;

    @Before
    public void initSubject() throws Exception {
        when(tsvReaderUtilsMock.build(anyString())).thenReturn(csvReaderMock);
        when(csvReaderMock.readNext()).thenReturn(header)
                .thenReturn(line)
                .thenReturn(null);

        when(geneNamesProviderMock.getGeneName("ens1")).thenReturn("name1");

        RnaSeqRawDataHeaderBuilder headerBuilder = new RnaSeqRawDataHeaderBuilder();

        subject = new ExpressionsWriterImpl(tsvReaderUtilsMock, geneNamesProviderMock);
        subject.setFileUrlTemplate("magetab/{0}/{0}-row-counts.tsv");
        subject.setHeaderBuilder(headerBuilder);
        subject.setResponseWriter(printWriterMock);
    }

    @Test
    public void testBuildHeader() throws Exception {
        String[] result = subject.buildHeader(header);
        assertThat(result, is(new String[]{HeaderBuilder.GENE_NAME, HeaderBuilder.GENE_ID, "SRR057596", "SRR057597", "SRR057598"}));
    }

    @Test
    public void testWrite() throws Exception {
        subject.setExperimentAccession("Exp1");
        Long count = subject.write();

        verify(printWriterMock).write("Gene name\tGene Id\tSRR057596\tSRR057597\tSRR057598\n", 0, 48);

        verify(printWriterMock).write("name1\tens1\t1\t0\t10.5\n", 0, 20);

        assertThat(count, is(1L));
    }
}
