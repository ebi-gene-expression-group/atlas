package uk.ac.ebi.atlas.bioentity.go;

import au.com.bytecode.opencsv.CSVReader;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.OntologyTerm;

import java.io.IOException;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class GoPoTermTSVParserTest {

    private final static String GO_0000001 = "GO:0000001";
    private final static String MITOCHONDRION_INHERITANCE = "mitochondrion inheritance";
    private final static int DEPTH_6 = 6;

    private final static String PO_0000001 = "PO:0000001";
    private final static String EMBRYO_PROPER = "embryo proper";

    private GoPoTSVParser subject;

    @Mock
    private CSVReader tsvReaderMock;

    @Before
    public void setUp() {
        subject = new GoPoTSVParser(tsvReaderMock);
    }

    @Test
    public void parseLineWithDepthField() throws IOException {
        //given
        given(tsvReaderMock.readNext())
                .willReturn(new String[] {GO_0000001, MITOCHONDRION_INHERITANCE, "biological_process", Integer.toString(DEPTH_6)})
                .willReturn(null);
        //when
        Map<String, OntologyTerm> map = subject.parse();

        //then
        assertThat(map.get(GO_0000001).accession(), is(GO_0000001));
        assertThat(map.get(GO_0000001).name(), is(MITOCHONDRION_INHERITANCE));
        assertThat(map.get(GO_0000001).depth(), is(DEPTH_6));
    }

    @Test
    public void duplicatesGetMinimumDepth() throws Exception {
        //given
        given(tsvReaderMock.readNext())
                .willReturn(new String[] {GO_0000001, MITOCHONDRION_INHERITANCE, "biological_process", Integer.toString(DEPTH_6 + 1)})
                .willReturn(new String[] {GO_0000001, MITOCHONDRION_INHERITANCE, "biological_process", Integer.toString(DEPTH_6)})
                .willReturn(new String[] {GO_0000001, MITOCHONDRION_INHERITANCE, "biological_process", Integer.toString(DEPTH_6 + 2)})
                .willReturn(null);
        //when
        Map<String, OntologyTerm> map = subject.parse();

        //then
        assertThat(map.get(GO_0000001).accession(), is(GO_0000001));
        assertThat(map.get(GO_0000001).name(), is(MITOCHONDRION_INHERITANCE));
        assertThat(map.get(GO_0000001).depth(), is(DEPTH_6));
    }

    @Test
    public void lineWithoutDepthGetsDefaultDepth() throws Exception {
        //given
        given(tsvReaderMock.readNext())
                .willReturn(new String[] {PO_0000001, EMBRYO_PROPER})
                .willReturn(null);

        //when
        Map<String, OntologyTerm> map = subject.parse();

        //then
        assertThat(map.get(PO_0000001).accession(), is(PO_0000001));
        assertThat(map.get(PO_0000001).name(), is(EMBRYO_PROPER));
        assertThat(map.get(PO_0000001).depth(), is(GoPoTSVParser.DEFAULT_DEPTH));
    }

    @Test
    public void ignoreLineWithoutValidAccessionPrefix() throws Exception {
        //given
        given(tsvReaderMock.readNext())
                .willReturn(new String[] {"foo:0000001", "bar"})
                .willReturn(null);

        //when
        Map<String, OntologyTerm> map = subject.parse();

        //then
        assertThat(map.isEmpty(), is(true));
    }

    @Test
    public void emptyFieldsAreIgnored() throws Exception {
        //given
        given(tsvReaderMock.readNext())
                .willReturn(new String[] {GO_0000001, "", "", "", "", ""})
                .willReturn(null);

        //when
        Map<String, OntologyTerm> map = subject.parse();

        //then
        assertThat(map.size(), is(1));
        assertThat(map.get(GO_0000001).name(), is(""));
        assertThat(map.get(GO_0000001).depth(), is(GoPoTSVParser.DEFAULT_DEPTH));
    }


    @Test
    public void emptyLinesAreIgnored() throws Exception {
        //given
        given(tsvReaderMock.readNext())
                .willReturn(new String[] {})
                .willReturn(new String[] {"", "", "", "", "", ""})
                .willReturn(null);

        //when
        Map<String, OntologyTerm> map = subject.parse();

        //then
        assertThat(map.isEmpty(), is(true));
    }

}