package uk.ac.ebi.atlas.geneindex;

import com.google.common.collect.Lists;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import uk.ac.ebi.atlas.utils.Files;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsString;
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

        List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();

        messageConverters.add(new MappingJacksonHttpMessageConverter());
//        restTemplate.setMessageConverters(messageConverters);


        subject = new IndexClient(restTemplate);

    }

//    @Test
    public void  testGetGeneIds() throws URISyntaxException {
        String result = subject.findGeneIdJson(Lists.newArrayList("binding", "GO:3A0016881"), "homo sapiens");

        assertThat(result, containsString("\"numFound\":"));
//        String enzyme = subject.findGeneIdJson(Lists.newArrayList("cancer", "GO:3A0016881"), "homo sapiens");
//        String enzyme = subject.findGeneIdJson(Lists.newArrayList("enzyme"), "homo sapiens");


    }

    @Test
    public void extractGeneIdentifiers(){
        assertThat(subject.extractGeneIds(jsonData), hasItems("ENSG00000113196", "ENSG00000166823"));
    }





}
