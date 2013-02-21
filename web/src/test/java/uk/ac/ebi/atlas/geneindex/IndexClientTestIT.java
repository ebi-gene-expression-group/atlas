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

import javax.inject.Inject;
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

    @Inject
    private SolrClient subject;

    private String query = "GO:0008134 \"p53 binding\"";

    private Set<String> species = Sets.newHashSet("Homo sapiens");

    @Test
    public void testFindGeneIdJsonValidQuery() throws URISyntaxException {
        //given
        String result = subject.findGeneIdJson(query, species);

        //some genes are found
        assertThat(result, containsString("[{\"identifier\":\"ENSG"));
    }


    @Test
    public void testFindGeneIdJsonNotExistingQuery() throws URISyntaxException {
        //given
        String query = "\"NOT THERE\"";

        String result = subject.findGeneIdJson(query, species);

        //no genes are found
        assertThat(result, containsString("\"numFound\":0"));
    }


}