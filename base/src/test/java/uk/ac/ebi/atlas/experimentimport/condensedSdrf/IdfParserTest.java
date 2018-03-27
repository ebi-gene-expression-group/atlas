package uk.ac.ebi.atlas.experimentimport.condensedSdrf;

import com.google.common.collect.ImmutableSet;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import uk.ac.ebi.atlas.commons.readers.TsvStreamer;

import java.util.stream.Stream;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class IdfParserTest {

    private static final String E_MTAB_513 = "E-MTAB-513";

    private static final String E_MTAB_513_TITLE =
            "RNA-Seq of human individual tissues and mixture of 16 tissues (Illumina Body Map)";
    private static final String E_MTAB_513_AE_DISPLAY_NAME =
            "Study investigating RNA-Seq of human individual tissues and mixture of 16 tissues";
    private static final String[] E_MTAB_513_PUBMED_IDS_ARRAY = {"22496456", "22955988", "23258890"};
    private static final ImmutableSet<String> E_MTAB_513_PUBMED_IDS = ImmutableSet.copyOf(E_MTAB_513_PUBMED_IDS_ARRAY);

    private static final String[][] E_MTAB_513_IDF_TXT = {
            {"Investigation Title", E_MTAB_513_TITLE},
            {"Comment[AEExperimentDisplayName]", E_MTAB_513_AE_DISPLAY_NAME},
            {"PubMed ID", E_MTAB_513_PUBMED_IDS_ARRAY[0], E_MTAB_513_PUBMED_IDS_ARRAY[1], E_MTAB_513_PUBMED_IDS_ARRAY[2]}
    };

    private static final String[][] E_MTAB_513_IDF_TXT_MIXED_CASE = {
            {"INVESTIGATION TITLE   ", E_MTAB_513_TITLE},
            {" comment[AEExperimentDisplayName]", E_MTAB_513_AE_DISPLAY_NAME},
            {"PubMed id", E_MTAB_513_PUBMED_IDS_ARRAY[0], E_MTAB_513_PUBMED_IDS_ARRAY[1], E_MTAB_513_PUBMED_IDS_ARRAY[2]}
    };


    @Mock
    private TsvStreamer titleIdfStreamerMock;

    @Mock
    private TsvStreamer pubmedsIdfStreamerMock;

    @Mock
    private IdfStreamerFactory idfStreamerFactoryMock;

    @InjectMocks
    private IdfParser subject;

    @Before
    public void setUp() {
        when(idfStreamerFactoryMock.create(E_MTAB_513)).thenReturn(titleIdfStreamerMock, pubmedsIdfStreamerMock);
    }

    @Test
    public void parse() {
        when(titleIdfStreamerMock.get()).thenReturn(Stream.of(E_MTAB_513_IDF_TXT));
        when(pubmedsIdfStreamerMock.get()).thenReturn(Stream.of(E_MTAB_513_IDF_TXT));

        Pair<String, ImmutableSet<String>> idfParserOutput = subject.parse(E_MTAB_513);

        assertThat(idfParserOutput.getLeft(), is(E_MTAB_513_AE_DISPLAY_NAME));
        assertThat(idfParserOutput.getRight(), is(E_MTAB_513_PUBMED_IDS));
    }

    @Test
    public void parseMixedCaseKeys() {
        when(titleIdfStreamerMock.get()).thenReturn(Stream.of(E_MTAB_513_IDF_TXT_MIXED_CASE));
        when(pubmedsIdfStreamerMock.get()).thenReturn(Stream.of(E_MTAB_513_IDF_TXT_MIXED_CASE));

        Pair<String, ImmutableSet<String>> idfParserOutput = subject.parse(E_MTAB_513);

        assertThat(idfParserOutput.getLeft(), is(E_MTAB_513_AE_DISPLAY_NAME));
        assertThat(idfParserOutput.getRight(), is(E_MTAB_513_PUBMED_IDS));
    }

    @Test
    public void parseNoPubmedIds() {
        when(titleIdfStreamerMock.get()).thenReturn(Stream.<String[]>of(E_MTAB_513_IDF_TXT[0], E_MTAB_513_IDF_TXT[1]));
        when(pubmedsIdfStreamerMock.get()).thenReturn(Stream.<String[]>of(E_MTAB_513_IDF_TXT[0], E_MTAB_513_IDF_TXT[1]));

        Pair<String, ImmutableSet<String>> idfParserOutput = subject.parse(E_MTAB_513);

        assertThat(idfParserOutput.getLeft(), is(E_MTAB_513_AE_DISPLAY_NAME));
        assertThat(idfParserOutput.getRight(), is(empty()));
    }

    @Test
    public void parseNoAeDisplayName() {
        when(titleIdfStreamerMock.get()).thenReturn(Stream.<String[]>of(E_MTAB_513_IDF_TXT[0], E_MTAB_513_IDF_TXT[2]));
        when(pubmedsIdfStreamerMock.get()).thenReturn(Stream.<String[]>of(E_MTAB_513_IDF_TXT[0], E_MTAB_513_IDF_TXT[2]));

        Pair<String, ImmutableSet<String>> idfParserOutput = subject.parse(E_MTAB_513);

        assertThat(idfParserOutput.getLeft(), is(E_MTAB_513_TITLE));
        assertThat(idfParserOutput.getRight(), is(E_MTAB_513_PUBMED_IDS));
    }

    @Test
    public void parseNoAeDisplayNameNoTitle() {
        when(titleIdfStreamerMock.get()).thenReturn(Stream.<String[]>of(E_MTAB_513_IDF_TXT[2]));
        when(pubmedsIdfStreamerMock.get()).thenReturn(Stream.<String[]>of(E_MTAB_513_IDF_TXT[2]));

        Pair<String, ImmutableSet<String>> idfParserOutput = subject.parse(E_MTAB_513);

        assertThat(idfParserOutput.getLeft().isEmpty(), is(true));
        assertThat(idfParserOutput.getRight(), is(E_MTAB_513_PUBMED_IDS));
    }

    @Test
    public void parseNothing() {
        when(titleIdfStreamerMock.get()).thenReturn(Stream.empty());
        when(pubmedsIdfStreamerMock.get()).thenReturn(Stream.empty());


        Pair<String, ImmutableSet<String>> idfParserOutput = subject.parse(E_MTAB_513);

        assertThat(idfParserOutput.getLeft().isEmpty(), is(true));
        assertThat(idfParserOutput.getRight(), is(empty()));
    }

}