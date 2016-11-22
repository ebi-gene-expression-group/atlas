
package uk.ac.ebi.atlas.experimentpage.experimentdesign;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.ui.Model;
import org.springframework.validation.support.BindingAwareModelMap;
import uk.ac.ebi.atlas.trader.ArrayDesignTrader;
import uk.ac.ebi.atlas.trader.ExperimentTrader;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContext.xml", "classpath:oracleContext.xml"})
public class DifferentialDesignPageControllerIT {

    private static final String EXPERIMENT_ACCESSION = "E-GEOD-25185";

    private ExperimentDesignPageRequestHandler subject;

    @Inject
    ArrayDesignTrader arrayDesignTrader;
    @Inject
    ExperimentTrader experimentTrader;

    private HttpServletRequest requestMock;

    Model model = new BindingAwareModelMap();


    @Before
    public void initSubject() throws Exception {
        subject = new ExperimentDesignPageRequestHandler(experimentTrader);

        requestMock = mock(HttpServletRequest.class);
        when(requestMock.getRequestURI()).thenReturn("/gxa/experiments/" + EXPERIMENT_ACCESSION + "/experiment-design");

        assertNotNull(experimentTrader.getExperiment(EXPERIMENT_ACCESSION, ""));
    }

    @Test
    public void testExtractExperimentDesign() throws IOException {

        // given
        subject.handleRequest(EXPERIMENT_ACCESSION,model,requestMock,"","");

        Gson gson = new Gson();

        // then
        Map<String, Object> map = model.asMap();

        assertNotNull(map.get("assayHeaders"));

        // and
        String[] samples = gson.fromJson((String) map.get("sampleHeaders"), String[].class);
        assertThat(samples.length, greaterThan(0));
        String[] factors = gson.fromJson((String) map.get("factorHeaders"), String[].class);
        assertThat(factors.length, greaterThan(0));

        // and
        Type listStringArrayType = new TypeToken<List<String[]>>() {
        }.getType();
        List<String[]> data = gson.fromJson((String) map.get("tableData"), listStringArrayType);
        assertThat(data.size(), greaterThan(0));


        // and
        assertNotNull(map.get("runAccessions"));

        // and
        assertThat((String) map.get("experimentAccession"), is(EXPERIMENT_ACCESSION));
    }
}