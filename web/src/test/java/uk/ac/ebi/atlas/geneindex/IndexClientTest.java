package uk.ac.ebi.atlas.geneindex;

import org.junit.Before;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;
import uk.ac.ebi.atlas.utils.Files;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;

public class IndexClientTest {

    private IndexClient subject;

    private String jsonData;

    @Before
    public void loadTestData() throws IOException {
        jsonData = Files.readTextFileFromClasspath(this.getClass(), "solr.json");
    }

    @Before
    public void initSubject() {
        RestTemplate restTemplate = new RestTemplate();
        GenePropertyQueryBuilder queryBuilder = new GenePropertyQueryBuilder();

        subject = new IndexClient(restTemplate, queryBuilder);
    }


    @Test
    public void extractGeneIdentifiers(){
        assertThat(subject.extractGeneIds(jsonData), hasItems("ENSG00000113196", "ENSG00000166823"));
    }





}
