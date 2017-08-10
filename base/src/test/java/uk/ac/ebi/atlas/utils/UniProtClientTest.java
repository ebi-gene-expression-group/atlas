package uk.ac.ebi.atlas.utils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.text.MessageFormat;
import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UniProtClientTest {

    private static final String UNIPROT_ID = "UNIPROT_ID";

    private UniProtClient subject;

    @Mock
    private RestTemplate restTemplateMock;

    @Before
    public void initSubject() throws Exception {
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

    @Test
    public void testFetchReactomeIds() throws Exception {
        String url = MessageFormat.format(UniProtClient.UNIPROT_URL, UNIPROT_ID);
        when(restTemplateMock.getForObject(url, String.class)).thenReturn("Entry\tCross-reference (reactome)\n" +
                "Q8N349\tREACT_111102;\n");
        assertThat(subject.fetchReactomeIds(UNIPROT_ID), containsInAnyOrder("REACT_111102"));
    }

    @Test
    public void testFetchReactomeIdsUniprotDown() throws Exception {
        String url = MessageFormat.format(UniProtClient.UNIPROT_URL, UNIPROT_ID);
        Mockito.doThrow(new HttpServerErrorException(HttpStatus.valueOf(503))).when(restTemplateMock).getForObject(url, String.class);
        assertThat(subject.fetchReactomeIds(UNIPROT_ID).size(), is(0));
    }

}
