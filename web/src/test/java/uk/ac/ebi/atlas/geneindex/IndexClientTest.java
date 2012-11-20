package uk.ac.ebi.atlas.geneindex;

import org.junit.Before;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

public class IndexClientTest {

    private IndexClient subject;

    @Before
    public void initSubject() {
        RestTemplate restTemplate = new RestTemplate();

        List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();

        messageConverters.add(new MappingJacksonHttpMessageConverter());
//        restTemplate.setMessageConverters(messageConverters);

//        GenePropertyQueryBuilder queryBuilder = mock(GenePropertyQueryBuilder.class);
        GenePropertyQueryBuilder queryBuilder = new GenePropertyQueryBuilder();

        subject = new IndexClient(restTemplate, queryBuilder);
        subject.setServerURL("http://localhost:8983/solr/");
    }

//    @Test
    public void  testGetGeneIds() throws URISyntaxException {
        String result = subject.findGeneIdJson("binding GO:3A0016881", "homo sapiens");

        assertThat(result, containsString("\"numFound\":"));

        Set<String> strings = subject.parseJson(result);
        System.out.println("strings = " + strings);
//        String enzyme = subject.findGeneIdJson(Lists.newArrayList("cancer", "GO:3A0016881"), "homo sapiens");
//        String enzyme = subject.findGeneIdJson(Lists.newArrayList("enzyme"), "homo sapiens");


    }




}
