package uk.ac.ebi.atlas.geneannotation.mirna;

import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class MirbaseFastaParserTest {

    public static final String ENTRY1 =
            ">rno-miR-551b-3p MIMAT0005596 Rattus norvegicus miR-551b-3p\n" +
                    "GGCGACCCAUACUUGGUUUCAGU\n";
    public static final String ENTRY2 =
            ">hsa-miR-548e-5p MIMAT0026736 Homo sapiens miR-548e-5p\n" +
                    "CAAAAGCAAUCGCGGUUUUUGC\n";

    private MirbaseFastaParser subject;

    @Before
    public void initSubject() throws Exception {
        subject = new MirbaseFastaParser();
    }

    @Test
    public void testParse() throws Exception {

    }

    @Test
    public void testReadData() throws Exception {
        BufferedReader reader = new BufferedReader(new StringReader(ENTRY1 + ENTRY2));
        List<MiRNAEntity> entities = subject.readData(reader);

        assertThat(entities.size(), is(2));
        assertThat(entities.get(0).getIdentifier(), is("rno-miR-551b-3p"));
        assertThat(entities.get(0).getSequence(), is("GGCGACCCAUACUUGGUUUCAGU"));

        assertThat(entities.get(1).getIdentifier(), is("hsa-miR-548e-5p"));
        assertThat(entities.get(1).getSequence(), is("CAAAAGCAAUCGCGGUUUUUGC"));
    }

    @Test
    public void testParseName() throws Exception {
        List<String> words = subject.splitLine(">rno-miR-551b-3p MIMAT0005596 Rattus norvegicus miR-551b-3p\n");

        assertThat(subject.parseName(words), is("miR-551b-3p"));
    }

    @Test
    public void testParseIdentifier() throws Exception {
        List<String> words = subject.splitLine(">rno-miR-551b-3p MIMAT0005596 Rattus norvegicus miR-551b-3p\n");

        assertThat(subject.parseIdentifier(words), is("rno-miR-551b-3p"));
    }

    @Test
    public void testParseAccession() throws Exception {
        List<String> words = subject.splitLine(">rno-miR-551b-3p MIMAT0005596 Rattus norvegicus miR-551b-3p\n");

        assertThat(subject.parseAccession(words), is("MIMAT0005596"));
    }

    @Test
    public void testParseOrganism() throws Exception {
        List<String> words = subject.splitLine(">rno-miR-551b-3p MIMAT0005596 Rattus norvegicus miR-551b-3p\n");

        assertThat(subject.parseOrganism(words), is("Rattus norvegicus"));

    }
}
