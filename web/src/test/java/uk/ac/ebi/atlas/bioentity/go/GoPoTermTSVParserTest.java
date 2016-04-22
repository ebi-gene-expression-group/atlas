package uk.ac.ebi.atlas.bioentity.go;

import au.com.bytecode.opencsv.CSVReader;
import groovy.util.slurpersupport.GPathResult;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.OntologyTerm;

import java.io.IOException;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class GoPoTermTSVParserTest {

    private final static String GO_0000001 = "GO:0000001";
    private final static String MITOCHONDRION_INHERITANCE = "mitochondrion inheritance";
    private final static int DEPTH_6 = 6;

    private final static String PO_0000001 = "PO:0000001";
    private final static String EMBRYO_PROPER = "embryo proper";

    private GoPoTermTSVParser subject;

    @Mock
    private CSVReader tsvReaderMock;

    @Before
    public void setUp() {
        subject = new GoPoTermTSVParser(tsvReaderMock);
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
        assertEquals(map.get(GO_0000001).accession(), GO_0000001);
        assertEquals(map.get(GO_0000001).name(), MITOCHONDRION_INHERITANCE);
        assertEquals(map.get(GO_0000001).depth(), DEPTH_6);
    }

    @Test
    public void duplicatesGetMinimumDepth() throws IOException {
        //given
        given(tsvReaderMock.readNext())
                .willReturn(new String[] {GO_0000001, MITOCHONDRION_INHERITANCE, "biological_process", Integer.toString(DEPTH_6 + 1)})
                .willReturn(new String[] {GO_0000001, MITOCHONDRION_INHERITANCE, "biological_process", Integer.toString(DEPTH_6)})
                .willReturn(new String[] {GO_0000001, MITOCHONDRION_INHERITANCE, "biological_process", Integer.toString(DEPTH_6 + 2)})
                .willReturn(null);
        //when
        Map<String, OntologyTerm> map = subject.parse();

        //then
        assertEquals(map.get(GO_0000001).accession(), GO_0000001);
        assertEquals(map.get(GO_0000001).name(), MITOCHONDRION_INHERITANCE);
        assertEquals(map.get(GO_0000001).depth(), DEPTH_6);
    }

    @Test
    public void lineWithoutDepthGetsDefaultDepth() throws IOException {
        //given
        given(tsvReaderMock.readNext())
                .willReturn(new String[] {PO_0000001, EMBRYO_PROPER})
                .willReturn(null);

        //when
        Map<String, OntologyTerm> map = subject.parse();

        //then
        assertEquals(map.get(PO_0000001).accession(), PO_0000001);
        assertEquals(map.get(PO_0000001).name(), EMBRYO_PROPER);
        assertEquals(map.get(PO_0000001).depth(), GoPoTermTSVParser.DEFAULT_DEPTH);
    }

    @Test
    public void ignoreLineWithoutValidAccessionPrefix() throws IOException {
        //given
        given(tsvReaderMock.readNext())
                .willReturn(new String[] {"foo:0000001", "bar"})
                .willReturn(null);

        //when
        Map<String, OntologyTerm> map = subject.parse();

        //then
        assertTrue(map.isEmpty());
    }

    @Test
    public void emptyFieldsAreIgnored() throws IOException {
        //given
        given(tsvReaderMock.readNext())
                .willReturn(new String[] {GO_0000001, "", "", "", "", ""})
                .willReturn(null);

        //when
        Map<String, OntologyTerm> map = subject.parse();

        //then
        assertEquals(map.size(), 1);
        assertEquals(map.get(GO_0000001).name(), "");
        assertEquals(map.get(GO_0000001).depth(), GoPoTermTSVParser.DEFAULT_DEPTH);
    }


    @Test
    public void emptyLinesAreIgnored() throws IOException {
        //given
        given(tsvReaderMock.readNext())
                .willReturn(new String[] {})
                .willReturn(new String[] {"", "", "", "", "", ""})
                .willReturn(null);

        //when
        Map<String, OntologyTerm> map = subject.parse();

        //then
        assertTrue(map.isEmpty());
    }

}