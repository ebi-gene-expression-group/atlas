package uk.ac.ebi.atlas.commands.download;

import au.com.bytecode.opencsv.CSVReader;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.geneannotation.GeneNamesProvider;
import uk.ac.ebi.atlas.geneannotation.arraydesign.DesignElementMappingProvider;
import uk.ac.ebi.atlas.utils.CsvReaderBuilder;

import java.io.PrintWriter;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MicroarrayDataWriterTest {

    public static final String ARRAY_DESIGN_ACC = "arrayDesign1";
    @Mock
    private CsvReaderBuilder csvReaderBuilderMock;

    @Mock
    private PrintWriter printWriterMock;

    @Mock
    private GeneNamesProvider geneNamesProviderMock;

    @Mock
    private DesignElementMappingProvider mappingProviderMock;

    @Mock
    private CSVReader csvReaderMock;

    private String[] header = {"DesignElementAccession", "C1", "C2", "C3"};
    private String[] line = {"de123", "1", "0", "10.5"};

    private MicroarrayDataWriter subject;

    @Before
    public void initSubject() throws Exception {
        when(csvReaderBuilderMock.buildCsvReader(anyString())).thenReturn(csvReaderMock);
        when(csvReaderMock.readNext()).thenReturn(header)
                .thenReturn(line)
                .thenReturn(null);

        when(geneNamesProviderMock.getGeneName("ens1")).thenReturn("name1");
        when(mappingProviderMock.getEnsGeneId(ARRAY_DESIGN_ACC, "de123")).thenReturn("ens1");

        MicroarrayNormalizedDataHeaderBuilder headerBuilder = new MicroarrayNormalizedDataHeaderBuilder();

        subject = new MicroarrayDataWriter(csvReaderBuilderMock, geneNamesProviderMock, mappingProviderMock);
        subject.setFileUrlTemplate("magetab/{0}/{0}-row-counts.tsv");
        subject.setHeaderBuilder(headerBuilder);
        subject.setResponseWriter(printWriterMock);
        subject.setArrayDesignAccession(ARRAY_DESIGN_ACC);
    }

    @Test
    public void testBuildHeader() throws Exception {
        String[] result = subject.buildHeader(header);
        assertThat(result, is(new String[]{HeaderBuilder.GENE_NAME, HeaderBuilder.DESIGN_ELEMENT, "C1", "C2", "C3"}));
    }

    @Test
    public void testWrite() throws Exception {
        subject.setExperimentAccession("Exp1");
        Long count = subject.write();

        verify(printWriterMock).write("Gene name\tDesign Element\tC1\tC2\tC3\n", 0, 34);

        verify(printWriterMock).write("name1\tde123\t1\t0\t10.5\n", 0, 21);

        assertThat(count, is(1L));
    }
}
