package uk.ac.ebi.atlas.bioentity.interpro;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import uk.ac.ebi.atlas.commons.readers.TsvStreamer;
import uk.ac.ebi.atlas.model.OntologyTerm;

import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class InterProTsvParserTest {
    private static final String IPR000001 = "IPR000001";
    private static final String KRINGLE = "Kringle";
    private static final String DOMAIN = "Domain";

    @Mock
    private TsvStreamer tsvStreamerMock;

    @Test
    void parseLine() {
        when(tsvStreamerMock.get())
                .thenReturn(Stream.<String[]>of(new String[] {KRINGLE, IPR000001, DOMAIN}));

        Map<String, OntologyTerm> map = InterProTsvParser.parse(tsvStreamerMock);

        assertThat(map.get(IPR000001))
                .isEqualTo(OntologyTerm.create(IPR000001, KRINGLE + " (" + DOMAIN.toLowerCase() + ")"));
    }

    @Test
    void ignoreLineWithoutValidAccessionPrefix() {
        when(tsvStreamerMock.get())
                .thenReturn(Stream.<String[]>of(new String[] {"foo:0000001", "bar"}));

        Map<String, OntologyTerm> map = InterProTsvParser.parse(tsvStreamerMock);

        assertThat(map).isEmpty();
    }

    @Test
    void incompleteLinesAreIgnored() {
        when(tsvStreamerMock.get())
                .thenReturn(Stream.<String[]>of(new String[] {IPR000001, "", "", "", "", ""}));

        Map<String, OntologyTerm> map = InterProTsvParser.parse(tsvStreamerMock);

        assertThat(map).isEmpty();
    }


    @Test
    void emptyLinesAreIgnored() {
        when(tsvStreamerMock.get())
                .thenReturn(Stream.of(new String[] {}, new String[] {"", "", "", "", "", ""}));

        Map<String, OntologyTerm> map = InterProTsvParser.parse(tsvStreamerMock);

        assertThat(map).isEmpty();
    }
}
