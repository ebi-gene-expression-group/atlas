package uk.ac.ebi.atlas.bioentity.interpro;

import au.com.bytecode.opencsv.CSVReader;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.bioentity.go.GoPoTermTSVParser;
import uk.ac.ebi.atlas.model.OntologyTerm;

import java.io.IOException;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class InterProTSVParserTest {

    private final static String IPR000001 = "IPR000001";
    private final static String KRINGLE = "Kringle";
    private final static String DOMAIN = "Domain";

    private InterProTSVParser subject;

    @Mock
    private CSVReader tsvReaderMock;

    @Before
    public void setUp() {
        subject = new InterProTSVParser(tsvReaderMock);
    }

    @Test
    public void parseLine() throws IOException {
        //given
        given(tsvReaderMock.readNext())
                .willReturn(new String[] {KRINGLE, IPR000001, DOMAIN})
                .willReturn(null);
        //when
        Map<String, String> map = subject.parse();

        //then
        assertEquals(map.get(IPR000001), KRINGLE + " (" + DOMAIN.toLowerCase()+ ")");
    }

    @Test
    public void ignoreLineWithoutValidAccessionPrefix() throws IOException {
        //given
        given(tsvReaderMock.readNext())
                .willReturn(new String[] {"foo:0000001", "bar"})
                .willReturn(null);

        //when
        Map<String, String> map = subject.parse();

        //then
        assertTrue(map.isEmpty());
    }

    @Test
    public void incompleteLinesAreIgnored() throws IOException {
        //given
        given(tsvReaderMock.readNext())
                .willReturn(new String[] {IPR000001, "", "", "", "", ""})
                .willReturn(null);

        //when
        Map<String, String> map = subject.parse();

        //then
        assertTrue(map.isEmpty());
    }


    @Test
    public void emptyLinesAreIgnored() throws IOException {
        //given
        given(tsvReaderMock.readNext())
                .willReturn(new String[] {})
                .willReturn(new String[] {"", "", "", "", "", ""})
                .willReturn(null);

        //when
        Map<String, String> map = subject.parse();

        //then
        assertTrue(map.isEmpty());
    }

}