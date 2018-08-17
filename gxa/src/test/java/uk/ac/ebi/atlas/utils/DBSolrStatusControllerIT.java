package uk.ac.ebi.atlas.utils;

import com.google.gson.reflect.TypeToken;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.configuration.WebConfig;

import javax.inject.Inject;

import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static uk.ac.ebi.atlas.utils.GsonProvider.GSON;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = WebConfig.class)
public class DBSolrStatusControllerIT {

    @Inject
    private DBSolrStatusController subject;

    @Test
    public void dbAndSolrStatus() {
        String message = subject.dbAndSolrStatus();
        Map<String, Object> status = GSON.fromJson(message, new TypeToken<Map<String, String>>() {}.getType());
        // Or the unsafer(?) Map status = new Gson().fromJson(message, Map.class);

        assertThat(status.get("DB"), is("UP"));
        assertThat(status.get("Solr"), is("UP"));
    }
}
