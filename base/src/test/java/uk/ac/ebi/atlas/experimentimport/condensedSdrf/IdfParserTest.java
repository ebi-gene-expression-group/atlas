package uk.ac.ebi.atlas.experimentimport.condensedSdrf;

import com.google.common.collect.ImmutableSet;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import uk.ac.ebi.atlas.commons.readers.TsvReader;

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
    private static final String[] E_MTAB_513_PUBMED_IDS_ARRAY = {"22496456", "22955988", "23258890"};
    private static final ImmutableSet<String> E_MTAB_513_PUBMED_IDS = ImmutableSet.copyOf(E_MTAB_513_PUBMED_IDS_ARRAY);

    private static final String[][] E_MTAB_513_IDF_TXT = {
        {"Investigation Title", E_MTAB_513_TITLE},
        {"PubMed ID", E_MTAB_513_PUBMED_IDS_ARRAY[0], E_MTAB_513_PUBMED_IDS_ARRAY[1], E_MTAB_513_PUBMED_IDS_ARRAY[2]}
    };

    @Mock
    private TsvReader titleIdfReaderMock;

    @Mock
    private TsvReader pubmedsIdfReaderMock;

    @Mock
    private IdfReaderFactory idfReaderFactoryMock;

    @InjectMocks
    private IdfParser subject;

    @Before
    public void setUp() {
        when(idfReaderFactoryMock.create(E_MTAB_513)).thenReturn(titleIdfReaderMock, pubmedsIdfReaderMock);
    }

    @Test
    public void parse() {
        when(titleIdfReaderMock.stream()).thenReturn(Stream.of(E_MTAB_513_IDF_TXT));
        when(pubmedsIdfReaderMock.stream()).thenReturn(Stream.of(E_MTAB_513_IDF_TXT));

        Pair<String, ImmutableSet<String>> idfParserOutput = subject.parse(E_MTAB_513);

        assertThat(idfParserOutput.getLeft(), is(E_MTAB_513_TITLE));
        assertThat(idfParserOutput.getRight(), is(E_MTAB_513_PUBMED_IDS));
    }

    @Test
    public void parseNoPubmedIds() {
        when(titleIdfReaderMock.stream()).thenReturn(Stream.<String[]>of(E_MTAB_513_IDF_TXT[0]));
        when(pubmedsIdfReaderMock.stream()).thenReturn(Stream.<String[]>of(E_MTAB_513_IDF_TXT[0]));

        Pair<String, ImmutableSet<String>> idfParserOutput = subject.parse(E_MTAB_513);

        assertThat(idfParserOutput.getLeft(), is(E_MTAB_513_TITLE));
        assertThat(idfParserOutput.getRight(), is(empty()));
    }

    @Test
    public void parseNoTitle() {
        when(titleIdfReaderMock.stream()).thenReturn(Stream.<String[]>of(E_MTAB_513_IDF_TXT[1]));
        when(pubmedsIdfReaderMock.stream()).thenReturn(Stream.<String[]>of(E_MTAB_513_IDF_TXT[1]));

        Pair<String, ImmutableSet<String>> idfParserOutput = subject.parse(E_MTAB_513);

        assertThat(idfParserOutput.getLeft().isEmpty(), is(true));
        assertThat(idfParserOutput.getRight(), is(E_MTAB_513_PUBMED_IDS));
    }

    @Test
    public void parseNothing() {
        when(titleIdfReaderMock.stream()).thenReturn(Stream.empty());
        when(pubmedsIdfReaderMock.stream()).thenReturn(Stream.empty());


        Pair<String, ImmutableSet<String>> idfParserOutput = subject.parse(E_MTAB_513);

        assertThat(idfParserOutput.getLeft().isEmpty(), is(true));
        assertThat(idfParserOutput.getRight(), is(empty()));
    }

}