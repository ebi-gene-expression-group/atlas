package uk.ac.ebi.atlas.utils;

import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.inject.Inject;

import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "/dispatcher-servlet.xml"})
public class DBSolrStatusControllerIT {

    @Inject
    private DBSolrStatusController subject;

    @Test
    public void dbAndSolrStatus() throws Exception {
        String message = subject.dbAndSolrStatus();
        Map<String, String> status = new Gson().fromJson(message, Map.class);
        assertThat(status.get("DB"), is("UP"));
        assertThat(status.get("Solr"), is("UP"));
    }
}