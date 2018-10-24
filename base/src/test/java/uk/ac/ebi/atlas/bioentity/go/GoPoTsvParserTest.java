package uk.ac.ebi.atlas.bioentity.go;

import au.com.bytecode.opencsv.CSVReader;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.OntologyTerm;

import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class GoPoTsvParserTest {
    private static final String GO_0000001 = "GO:0000001";
    private static final String MITOCHONDRION_INHERITANCE = "mitochondrion inheritance";
    private static final int DEPTH_6 = 6;

    private static final String PO_0000001 = "PO:0000001";
    private static final String EMBRYO_PROPER = "embryo proper";

    private GoPoTsvParser subject;

    @Mock
    private CSVReader tsvReaderMock;

    @Before
    public void setUp() {
        subject = new GoPoTsvParser(tsvReaderMock);
    }

    @Test
    public void parseLineWithDepthField() throws Exception {
        given(tsvReaderMock.readNext())
                .willReturn(new String[] {
                        GO_0000001, MITOCHONDRION_INHERITANCE, Integer.toString(DEPTH_6)})
                .willReturn(null);

        Map<String, OntologyTerm> map = subject.parse();

        assertThat(map.get(GO_0000001).accession(), is(GO_0000001));
        assertThat(map.get(GO_0000001).name(), is(MITOCHONDRION_INHERITANCE));
        assertThat(map.get(GO_0000001).depth(), is(DEPTH_6));
    }

    @Test
    public void duplicatesGetMinimumDepth() throws Exception {
        given(tsvReaderMock.readNext())
                .willReturn(new String[]
                        {GO_0000001, MITOCHONDRION_INHERITANCE, Integer.toString(DEPTH_6 + 1)})
                .willReturn(new String[]
                        {GO_0000001, MITOCHONDRION_INHERITANCE, Integer.toString(DEPTH_6)})
                .willReturn(new String[]
                        {GO_0000001, MITOCHONDRION_INHERITANCE, Integer.toString(DEPTH_6 + 2)})
                .willReturn(null);

        Map<String, OntologyTerm> map = subject.parse();

        assertThat(map.get(GO_0000001).accession(), is(GO_0000001));
        assertThat(map.get(GO_0000001).name(), is(MITOCHONDRION_INHERITANCE));
        assertThat(map.get(GO_0000001).depth(), is(DEPTH_6));
    }

    @Test
    public void lineWithoutDepthGetsDefaultDepth() throws Exception {
        given(tsvReaderMock.readNext())
                .willReturn(new String[] {PO_0000001, EMBRYO_PROPER})
                .willReturn(null);

        Map<String, OntologyTerm> map = subject.parse();

        assertThat(map.get(PO_0000001).accession(), is(PO_0000001));
        assertThat(map.get(PO_0000001).name(), is(EMBRYO_PROPER));
        assertThat(map.get(PO_0000001).depth(), is(GoPoTsvParser.DEFAULT_DEPTH));
    }

    @Test
    public void ignoreLineWithoutValidAccessionPrefix() throws Exception {
        given(tsvReaderMock.readNext())
                .willReturn(new String[] {"foo:0000001", "bar"})
                .willReturn(null);

        Map<String, OntologyTerm> map = subject.parse();

        assertThat(map.isEmpty(), is(true));
    }

    @Test
    public void emptyFieldsAreIgnored() throws Exception {
        given(tsvReaderMock.readNext())
                .willReturn(new String[] {GO_0000001, "", "", "", "", ""})
                .willReturn(null);

        Map<String, OntologyTerm> map = subject.parse();

        assertThat(map.size(), is(1));
        assertThat(map.get(GO_0000001).name(), is(""));
        assertThat(map.get(GO_0000001).depth(), is(GoPoTsvParser.DEFAULT_DEPTH));
    }


    @Test
    public void emptyLinesAreIgnored() throws Exception {
        given(tsvReaderMock.readNext())
                .willReturn(new String[] {})
                .willReturn(new String[] {"", "", "", "", "", ""})
                .willReturn(null);

        Map<String, OntologyTerm> map = subject.parse();

        assertThat(map.isEmpty(), is(true));
    }
}
