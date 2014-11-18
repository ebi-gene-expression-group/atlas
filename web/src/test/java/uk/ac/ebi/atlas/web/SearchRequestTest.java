package uk.ac.ebi.atlas.web;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class SearchRequestTest {

    private SearchRequest subject;

    @Before
    public void initSubject() {
        subject = new SearchRequest();
    }

    @Test
    public void tagsToQueryString_singleSingleTermTag() {
        String parseString = subject.tagsToQueryString("ASPM");
        assertThat(parseString, is("ASPM"));
    }

    @Test
    public void tagsToQueryString_singleSingleTermTagWithLeadingAndTrailingSpaces() {
        String parseString = subject.tagsToQueryString(" ASPM ");
        assertThat(parseString, is("ASPM"));
    }

    @Test
    public void tagsToQueryString_multipleTagsSingleTerms() {
        String parseString = subject.tagsToQueryString("ENTPD1\tBRCA1");
        assertThat(parseString, is("ENTPD1 BRCA1"));
    }

    @Test
    public void tagsToQueryString_singleMultiTermTagWithoutQuotes() {
        String parseString = subject.tagsToQueryString("zinc finger protein");
        assertThat(parseString, is("\"zinc finger protein\""));
    }

    @Test
    public void tagsToQueryString_singleMultiTermTagWithQuotes() {
        String parseString = subject.tagsToQueryString("\"zinc finger protein\"");
        assertThat(parseString, is("\"zinc finger protein\""));
    }

    @Test
    public void tagsToQueryString_multipleTags() {
        String parseString = subject.tagsToQueryString("zinc finger protein\tASPM");
        assertThat(parseString, is("\"zinc finger protein\" ASPM"));

        String parseString2 = subject.tagsToQueryString("zinc finger protein\tASPM\tLPHN2 ");
        assertThat(parseString2, is("\"zinc finger protein\" ASPM LPHN2"));
    }

    @Test
    public void tagsToQueryString_emptyTag() {
        String parseString = subject.tagsToQueryString("");
        assertThat(parseString, is(""));
    }

    @Test
    public void queryStringWithAComma_doNotSplitOnCommaInsideQuotes() {
        assertThat(subject.tagsToQueryString("binding\t\"mRNA splicing, via spliceosome\""), is("binding \"mRNA splicing, via spliceosome\""));
    }

    @Test
    public void queryStringToTags_multipleWords() {
        String parseString = subject.queryStringToTags("one two three");
        assertThat(parseString, is("one\ttwo\tthree"));
    }

    @Test
    public void queryStringToTags_wordsInQuotes() {
        String parseString = subject.queryStringToTags("\"zinc finger protein\" ASPM");
        assertThat(parseString, is("zinc finger protein\tASPM"));
    }

    @Test
    public void queryStringToTags_wordsInQuotesWithComma() {
        assertThat(subject.queryStringToTags("binding \"mRNA splicing, via spliceosome\""), is("binding\tmRNA splicing, via spliceosome"));
    }

    @Test
    public void queryStringToTags_singleWord() {
        String parseString = subject.queryStringToTags("one");
        assertThat(parseString, is("one"));
    }

    @Test
    public void queryStringToTags_emptyString() {
        String parseString = subject.queryStringToTags("");
        assertThat(parseString, is(""));
    }

}