package uk.ac.ebi.atlas.experimentimport.condensedSdrf;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.commons.readers.impl.TsvReaderImpl;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class IdfParserTest {

    private static final String E_MTAB_513 = "E-MTAB-513";

    private static final String E_MTAB_513_TITLE = "RNA-Seq of human individual tissues and mixture of 16 tissues (Illumina Body Map)";
    private static final String[] E_MTAB_513_PUBMED_IDS_ARRAY = {"22496456", "22955988", "23258890"};
    private static final ImmutableSet<String> E_MTAB_513_PUBMED_IDS = ImmutableSet.copyOf(E_MTAB_513_PUBMED_IDS_ARRAY);

    private static final String[][] E_MTAB_513_IDF_TXT = {
        {"Investigation Title", E_MTAB_513_TITLE},
        {"PubMed ID", E_MTAB_513_PUBMED_IDS_ARRAY[0], E_MTAB_513_PUBMED_IDS_ARRAY[1], E_MTAB_513_PUBMED_IDS_ARRAY[2]}
    };

    @Mock
    TsvReaderImpl tsvReaderImplMock;

    @Mock
    private IdfReaderFactory idfReaderFactoryMock;

    @InjectMocks
    private IdfParser subject;

    @Test
    public void parse() {
        //given
        given(idfReaderFactoryMock.create(E_MTAB_513)).willReturn(tsvReaderImplMock);
        given(tsvReaderImplMock.readAll()).willReturn(ImmutableList.copyOf(E_MTAB_513_IDF_TXT));

        //when
        Pair<String, ImmutableSet<String>> idfParserOutput = subject.parse(E_MTAB_513);

        //then
        assertThat(idfParserOutput.getLeft(), is(E_MTAB_513_TITLE));
        assertThat(idfParserOutput.getRight(), is(E_MTAB_513_PUBMED_IDS));
    }

    @Test
    public void parseNoPubmedIds() {
        //given
        given(idfReaderFactoryMock.create(E_MTAB_513)).willReturn(tsvReaderImplMock);
        given(tsvReaderImplMock.readAll()).willReturn(ImmutableList.of(E_MTAB_513_IDF_TXT[0]));

        //when
        Pair<String, ImmutableSet<String>> idfParserOutput = subject.parse(E_MTAB_513);

        //then
        assertThat(idfParserOutput.getLeft(), is(E_MTAB_513_TITLE));
        assertThat(idfParserOutput.getRight(), is(empty()));
    }

    @Test
    public void parseNoTitle() {
        //given
        given(idfReaderFactoryMock.create(E_MTAB_513)).willReturn(tsvReaderImplMock);
        given(tsvReaderImplMock.readAll()).willReturn(ImmutableList.of(E_MTAB_513_IDF_TXT[1]));

        //when
        Pair<String, ImmutableSet<String>> idfParserOutput = subject.parse(E_MTAB_513);

        //then
        assertThat(idfParserOutput.getLeft().isEmpty(), is(true));
        assertThat(idfParserOutput.getRight(), is(E_MTAB_513_PUBMED_IDS));
    }

    @Test
    public void parseNothing() {
        ImmutableList<String[]> emptyList = ImmutableList.of();
        //given
        given(idfReaderFactoryMock.create(E_MTAB_513)).willReturn(tsvReaderImplMock);
        given(tsvReaderImplMock.readAll()).willReturn(ImmutableList.copyOf(emptyList));

        //when
        Pair<String, ImmutableSet<String>> idfParserOutput = subject.parse(E_MTAB_513);

        //then
        assertThat(idfParserOutput.getLeft().isEmpty(), is(true));
        assertThat(idfParserOutput.getRight(), is(empty()));
    }

}