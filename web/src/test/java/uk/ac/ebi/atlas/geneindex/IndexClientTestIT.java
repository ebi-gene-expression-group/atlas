package uk.ac.ebi.atlas.geneindex;

import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;
import uk.ac.ebi.atlas.utils.Files;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class IndexClientTestIT {

    private SolrClient subject;

    private GenePropertyQueryBuilder queryBuilder;

    private String jsonData;

    private Set<String> species = Sets.newHashSet("Homo sapiens");

    @Value("#{configuration['index.server.url']}")
    private String solrURL;

    @Before
    public void loadTestData() throws IOException {
        jsonData = Files.readTextFileFromClasspath(this.getClass(), "solr.json");
    }

    @Before
    public void initSubject() {
        RestTemplate restTemplate = new RestTemplate();

        queryBuilder = mock(GenePropertyQueryBuilder.class);

        subject = new SolrClient(restTemplate, queryBuilder);

        subject.setServerURL(solrURL);
    }

    @Test
    public void testFindGeneIdJsonValidQuery() throws URISyntaxException {
        //given
        when(queryBuilder.buildQueryString(anyString())).thenReturn("(alltext:\"GO:0008134\" OR alltext:\"p53 " +
                "binding\")");

        String result = subject.findGeneIdJson(anyString(), species);

        //some genes are found
        assertThat(result, containsString("[{\"identifier\":\"ENSG"));
    }


    @Test
    public void testFindGeneIdJsonNotExistingQuery() throws URISyntaxException {
        //given
        when(queryBuilder.buildQueryString(anyString())).thenReturn("(alltext:\"NOT THERE\")");

        String result = subject.findGeneIdJson(anyString(), species);

        //no genes are found
        assertThat(result, containsString("\"numFound\":0"));
    }


}