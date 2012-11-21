package uk.ac.ebi.atlas.geneindex;

import org.junit.Before;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;
import uk.ac.ebi.atlas.utils.Files;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class IndexClientTestIT {

    private IndexClient subject;

    private GenePropertyQueryBuilder queryBuilder;

    private String jsonData;

    @Before
    public void loadTestData() throws IOException {
        jsonData = Files.readTextFileFromClasspath(this.getClass(), "solr.json");
    }

    @Before
    public void initSubject() {
        RestTemplate restTemplate = new RestTemplate();

        queryBuilder = mock(GenePropertyQueryBuilder.class);

        subject = new IndexClient(restTemplate, queryBuilder);
        subject.setServerURL("http://lime:8983/solr/");
    }

    @Test
    public void testFindGeneIdJsonValidQuery() throws URISyntaxException {
        //given
        when(queryBuilder.buildQueryString(anyString())).thenReturn("(alltext:\"GO:0008134\" OR alltext:\"p53 " +
                "binding\")");

        String result = subject.findGeneIdJson(anyString(), "homo sapiens");

        //some genes are found
        assertThat(result, containsString("[{\"identifier\":\"ENSG"));
    }


    @Test
    public void testFindGeneIdJsonNotExistingQuery() throws URISyntaxException {
        //given
        when(queryBuilder.buildQueryString(anyString())).thenReturn("(alltext:\"NOT THERE\")");

        String result = subject.findGeneIdJson(anyString(), "homo sapiens");

        //no genes are found
        assertThat(result, containsString("\"numFound\":0"));
    }


}
