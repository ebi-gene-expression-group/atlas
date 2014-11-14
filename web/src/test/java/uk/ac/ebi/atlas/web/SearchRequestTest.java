package uk.ac.ebi.atlas.web;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class SearchRequestTest {

    private SearchRequest subject;

    private static final String TAG_STRING_1 = "zinc finger protein, ASPM";
    private static final String TAG_STRING_2 = "zinc finger protein";
    private static final String TAG_STRING_3 = "ASPM";
    private static final String TAG_STRING_4 = "zinc finger protein,ASPM,LPHN2 ";

    private static final String STRING_TAG_1 = "\"zinc finger protein\" ASPM";
    private static final String STRING_TAG_2 = "\"zinc finger protein\" ASPM protein";

    @Before
    public void initSubject() {
        subject = new SearchRequest();
    }

    @Test
    public void testMultipleTags() {
        String parseString = subject.parseTagsTextWithQuotes(TAG_STRING_1);
        assertThat(parseString, is("\"zinc finger protein\" ASPM"));

        String parseString2 = subject.parseTagsTextWithQuotes(TAG_STRING_4);
        assertThat(parseString2, is("\"zinc finger protein\" ASPM LPHN2"));
    }

    @Test
    public void testSingleTagWithMultiTerm() {
        String parseString = subject.parseTagsTextWithQuotes(TAG_STRING_2);
        assertThat(parseString, is("\"zinc finger protein\""));

    }

    @Test
    public void testSingleTagWithSingleTerm() {
        String parseString = subject.parseTagsTextWithQuotes(TAG_STRING_3);
        assertThat(parseString, is("ASPM"));
    }

    @Test
    public void testEmptyTag() {
        String parseString = subject.parseTagsTextWithQuotes("");
        assertThat(parseString, is(""));
    }

    @Test
    public void testTermToTag() {
        String parseString = subject.parseStringToTags(STRING_TAG_1);
        assertThat(parseString, is("zinc finger protein,ASPM"));
    }

    @Test
    public void testMultiTermToTag() {
        String parseString = subject.parseStringToTags(STRING_TAG_2);
        assertThat(parseString, is("zinc finger protein,ASPM,protein"));
    }


}