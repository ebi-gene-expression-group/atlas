package uk.ac.ebi.atlas.utils;

import org.junit.Before;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.Mockito.mock;

public class UniProtClientTest {

    private UniProtClient subject;

    @Before
    public void initSubject() throws Exception {
        RestTemplate restTemplateMock = mock(RestTemplate.class);
        subject = new UniProtClient(restTemplateMock);
    }

    @Test
    public void testParseResultWithTwoIds() throws Exception {
        Collection<String> strings = subject.parseResult("Entry\tCross-reference (reactome)\n" +
                "P05026\tREACT_15518;REACT_604;");

        assertThat(strings, containsInAnyOrder("REACT_15518", "REACT_604"));
    }

    @Test
    public void testParseResultWithOneId() throws Exception {
        Collection<String> strings = subject.parseResult("Entry\tCross-reference (reactome)\n" +
                "Q8N349\tREACT_111102;\n");

        assertThat(strings, containsInAnyOrder("REACT_111102"));
    }
}
