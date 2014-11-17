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
    public void singleSingleTermTag() {
        String parseString = subject.tagsToQueryString("ASPM");
        assertThat(parseString, is("ASPM"));
    }

    @Test
    public void singleMultiTermTagWithoutQuotes() {
        String parseString = subject.tagsToQueryString("zinc finger protein");
        assertThat(parseString, is("\"zinc finger protein\""));
    }

    @Test
    public void multipleTags() {
        String parseString = subject.tagsToQueryString("zinc finger protein, ASPM");
        assertThat(parseString, is("\"zinc finger protein\" ASPM"));

        String parseString2 = subject.tagsToQueryString("zinc finger protein,ASPM,LPHN2 ");
        assertThat(parseString2, is("\"zinc finger protein\" ASPM LPHN2"));
    }

    @Test
    public void emptyTag() {
        String parseString = subject.tagsToQueryString("");
        assertThat(parseString, is(""));
    }

    @Test
    public void queryStringToTags() {
        String parseString = subject.queryStringToTags("\"zinc finger protein\" ASPM");
        assertThat(parseString, is("zinc finger protein,ASPM"));
    }



}