package uk.ac.ebi.atlas.geneindex;

import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

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


        subject = new IndexClient(restTemplate);
    }

    @Test
    public void  testGetGeneIds() throws URISyntaxException {
        String result = subject.findGeneIdJson(Lists.newArrayList("binding", "GO:3A0016881"), "homo sapiens");

        assertThat(result, containsString("\"numFound\":15060"));
//        String enzyme = subject.findGeneIdJson(Lists.newArrayList("cancer", "GO:3A0016881"), "homo sapiens");
//        String enzyme = subject.findGeneIdJson(Lists.newArrayList("enzyme"), "homo sapiens");


    }




}
