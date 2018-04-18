package uk.ac.ebi.atlas.experimentimport.idf;

import com.google.common.collect.ImmutableSet;
import org.apache.commons.lang.math.NumberUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import uk.ac.ebi.atlas.commons.readers.TsvStreamer;
import uk.ac.ebi.atlas.model.Publication;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyString;
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
    private static final String[] E_MTAB_513_PUBLICATIONS_ARRAY = {"Publication 1", "Another publication", "Yet another publication"};
    private static final ImmutableSet<String> E_MTAB_513_PUBMED_IDS = ImmutableSet.copyOf(E_MTAB_513_PUBMED_IDS_ARRAY);
    private static final String E_MTAB_513_EXPECTED_CLUSTERS = "5";

    private static final String[][] E_MTAB_513_IDF_TXT = {
            {"Investigation Title", E_MTAB_513_TITLE},
            {"Comment[AEExperimentDisplayName]", E_MTAB_513_AE_DISPLAY_NAME},
            {"PubMed ID", E_MTAB_513_PUBMED_IDS_ARRAY[0], E_MTAB_513_PUBMED_IDS_ARRAY[1], E_MTAB_513_PUBMED_IDS_ARRAY[2]},
            {"Publication Title", E_MTAB_513_PUBLICATIONS_ARRAY[0], E_MTAB_513_PUBLICATIONS_ARRAY[1], E_MTAB_513_PUBLICATIONS_ARRAY[2]},
            {"Comment[ExpectedClusters]", E_MTAB_513_EXPECTED_CLUSTERS}
    };

    private static final String[][] E_MTAB_513_IDF_TXT_MIXED_CASE = {
            {"INVESTIGATION TITLE   ", E_MTAB_513_TITLE},
            {" comment[AEExperimentDisplayName]", E_MTAB_513_AE_DISPLAY_NAME},
            {"PubMed id", E_MTAB_513_PUBMED_IDS_ARRAY[0], E_MTAB_513_PUBMED_IDS_ARRAY[1], E_MTAB_513_PUBMED_IDS_ARRAY[2]},
            {"publication title", E_MTAB_513_PUBLICATIONS_ARRAY[0], E_MTAB_513_PUBLICATIONS_ARRAY[1], E_MTAB_513_PUBLICATIONS_ARRAY[2]},
            {"comment[expectedclusters]", E_MTAB_513_EXPECTED_CLUSTERS}
    };

    @Mock
    private TsvStreamer idfStreamerMock;

    @Mock
    private IdfStreamerFactory idfStreamerFactoryMock;

    @InjectMocks
    private IdfParser subject;

    @Before
    public void setUp() {
        when(idfStreamerFactoryMock.create(E_MTAB_513)).thenReturn(idfStreamerMock);
    }

    @Test
    public void parse() {
        when(idfStreamerMock.get()).thenReturn(Stream.of(E_MTAB_513_IDF_TXT));

        IdfParserOutput idfParserOutput = subject.parse(E_MTAB_513);

        assertThat(idfParserOutput.getTitle(), is(E_MTAB_513_AE_DISPLAY_NAME));
        assertThat(idfParserOutput.getPubmedIds(), is(E_MTAB_513_PUBMED_IDS));
        assertThat(idfParserOutput.getExpectedClusters(), is(NumberUtils.toInt(E_MTAB_513_EXPECTED_CLUSTERS)));
    }

    @Test
    public void parseMixedCaseKeys() {
        when(idfStreamerMock.get()).thenReturn(Stream.of(E_MTAB_513_IDF_TXT_MIXED_CASE));

        IdfParserOutput idfParserOutput = subject.parse(E_MTAB_513);

        assertThat(idfParserOutput.getTitle(), is(E_MTAB_513_AE_DISPLAY_NAME));
        assertThat(idfParserOutput.getPubmedIds(), is(E_MTAB_513_PUBMED_IDS));
        assertThat(idfParserOutput.getExpectedClusters(), is(NumberUtils.toInt(E_MTAB_513_EXPECTED_CLUSTERS)));
    }

    @Test
    public void parseNoPubmedIds() {
        when(idfStreamerMock.get()).thenReturn(Stream.<String[]>of(E_MTAB_513_IDF_TXT[0], E_MTAB_513_IDF_TXT[1]));

        IdfParserOutput idfParserOutput = subject.parse(E_MTAB_513);

        assertThat(idfParserOutput.getTitle(), is(E_MTAB_513_AE_DISPLAY_NAME));
        assertThat(idfParserOutput.getPublications().isEmpty(), is(true));
    }

    @Test
    public void parseNoAeDisplayName() {
        when(idfStreamerMock.get()).thenReturn(Stream.<String[]>of(E_MTAB_513_IDF_TXT[0], E_MTAB_513_IDF_TXT[2], E_MTAB_513_IDF_TXT[3]));

        IdfParserOutput idfParserOutput = subject.parse(E_MTAB_513);

        assertThat(idfParserOutput.getTitle(), is(E_MTAB_513_TITLE));
        assertThat(idfParserOutput.getPubmedIds(), is(E_MTAB_513_PUBMED_IDS));
    }

    @Test
    public void parseNoAeDisplayNameNoTitle() {
        when(idfStreamerMock.get()).thenReturn(Stream.<String[]>of(E_MTAB_513_IDF_TXT[2], E_MTAB_513_IDF_TXT[3]));

        IdfParserOutput idfParserOutput = subject.parse(E_MTAB_513);

        assertThat(idfParserOutput.getTitle(), isEmptyString());
        assertThat(idfParserOutput.getPubmedIds(), is(E_MTAB_513_PUBMED_IDS));
    }

    @Test
    public void parseNothing() {
        when(idfStreamerMock.get()).thenReturn(Stream.empty());

        IdfParserOutput idfParserOutput = subject.parse(E_MTAB_513);

        assertThat(idfParserOutput.getTitle(), isEmptyString());
        assertThat(idfParserOutput.getPublications().isEmpty(), is(true));
        assertThat(idfParserOutput.getExpectedClusters(), is(0));
    }

    @Test(expected = IdfParser.IdfParserException.class)
    public void parsePubmedIdsWithNoPublicationTitles() {
        when(idfStreamerMock.get()).thenReturn(Stream.<String[]>of(E_MTAB_513_IDF_TXT[2]));

        subject.parse(E_MTAB_513);
    }
}