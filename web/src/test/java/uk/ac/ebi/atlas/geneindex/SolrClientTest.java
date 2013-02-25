package uk.ac.ebi.atlas.geneindex;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;
import uk.ac.ebi.atlas.utils.Files;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class SolrClientTest {

    private SolrClient subject;

    private String jsonData;

    @Before
    public void loadTestData() throws IOException {
        jsonData = Files.readTextFileFromClasspath(this.getClass(), "solr.json");
    }

    @Before
    public void initSubject() {
        RestTemplate restTemplate = new RestTemplate();
        GenePropertyQueryBuilder queryBuilder = new GenePropertyQueryBuilder();

        subject = new SolrClient(restTemplate, queryBuilder);
    }

    @Test
    public void toUppercaseShouldConvertAllStringsToUppercase(){
        assertThat(subject.toUppercase(Lists.newArrayList("hEllo", "bOy")),containsInAnyOrder("HELLO", "BOY"));
    }

    @Test
    public void buildSpeciesQueryShouldJoinWithOrAndWrapInParenthesis(){
        String speciesQuery = subject.buildSpeciesQuery(Sets.newHashSet("Species 1", "Species 2"));
        assertThat(speciesQuery, startsWith("(\"species"));
        assertThat(speciesQuery, endsWith("\")"));
        assertThat(speciesQuery, containsString("species 1"));
        assertThat(speciesQuery, containsString("species 2"));
    }


}
