package uk.ac.ebi.atlas.geneindex;

import com.google.common.collect.Sets;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.inject.Inject;
import java.net.URISyntaxException;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

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
        Set<String> result = subject.findGeneIds(query, species);

        //some genes are found
        assertThat(result.iterator().next(), startsWith("ENSG"));
    }


    @Test
    public void testFindGeneIdJsonNotExistingQuery() throws URISyntaxException {
        //given
        String query = "\"NOT THERE\"";

        Set<String> result = subject.findGeneIds(query, species);

        //no genes are found
        assertThat(result, is(empty()));
    }


}